package com.workflow.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spire.pdf.PdfDocument;
import com.spire.pdf.fields.PdfField;
import com.spire.pdf.widget.PdfCheckBoxWidgetFieldWidget;
import com.spire.pdf.widget.PdfFormWidget;
import com.spire.pdf.widget.PdfTextBoxFieldWidget;
import com.workflow.exception.ResourceNotFoundException;
import com.workflow.model.FilledForm;
import com.workflow.model.Form;
import com.workflow.model.Ticket;
import com.workflow.model.TicketComment;
import com.workflow.model.TicketFile;
import com.workflow.model.User;
import com.workflow.repository.FilledFormRepository;
import com.workflow.repository.TicketRepository;
import com.workflow.repository.UserRepository;
import com.workflow.schema.Decision;
import com.workflow.schema.Hybrid;
import com.workflow.schema.Stage;
import com.workflow.utils.Email;

@Service
public class TicketService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private TicketRepository ticketRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private FilledFormRepository filledFormRepository;

	@Autowired
	private RestClientUpload restClientUpload;

	@Autowired
	private BlockService blockService;

	public void ticketDecision(String decision, Long id, String path) {

		Ticket ticket = ticketRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Ticket not exist with id :" + id));

		ObjectMapper mapper = new ObjectMapper();
		Stage[] stageList;
		User currentUser = ticket.getCurrentPerson();
		try {
			stageList = mapper.readValue(ticket.getWorkflow().getWorkflowMetadata(), Stage[].class);
			String oldStage = ticket.getCurrentStage();
			
			String chainData = ticket.getCurrentPerson().getFirstName() + " "
					+ ticket.getCurrentPerson().getLastName() + " - " + decision;
			for (Stage stage : stageList) {
				if (ticket.getCurrentStage().equals(stage.getName())) {
					for (Decision stageDecision : stage.getDecision()) {
						if (stageDecision.getName().equals(decision)) {
							if (!stageDecision.getMoveTo().equals("done")) {
								for (Stage nextStage : stageList) {
									if (nextStage.getName().equals(stageDecision.getMoveTo())) {
										JSONObject userInDifferentStage = new JSONObject(
												ticket.getUserInDifferentStage());
										String user = userInDifferentStage.getString(nextStage.getUser());
										User nextUser = userRepository.findByFirstName(user);
										ticket.setCurrentPerson(nextUser);
										if(path.equals("Hybrid")) {
											chainData = "Hybrid Path - Found " + currentUser.getFirstName() + " " + currentUser.getLastName() + " signature - move ticket to " + nextUser.getFirstName() + " " + nextUser.getLastName() + " queue";
										} else {
											chainData = chainData + " currently in  " + nextUser.getFirstName() + " " + nextUser.getLastName() + " queue";
										}
										Email.send(nextUser);
										break;
									}
								}
							} else {
								ticket.setCurrentStatus(stageDecision.getStatus());
								ticket.setCurrentPerson(null);
							}
							ticket.setCurrentStage(stageDecision.getMoveTo());
							break;
						}
					}
				}
				if (!ticket.getCurrentStage().equals(oldStage)) {
					blockService.addBlockIntoTicket(ticket, chainData);
					ticketRepository.save(ticket);
					break;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(ticket.getCurrentPerson() != null) {
			logger.info("ticket id " + ticket.getId() + " moved from user " + currentUser.getFirstName() + " to user " + ticket.getCurrentPerson().getFirstName());
		} else {
			logger.info("ticket id " + ticket.getId() + " moved to done state");
		}
	}

	public Map<String, Map<String, String>> extractData(Long id) {

		Ticket ticket = ticketRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Ticket not exist with id :" + id));

		Map<String, Map<String, String>> formSignatureDecisionMap = new HashMap<String, Map<String, String>>();
		ObjectMapper mapper = new ObjectMapper();

		for (TicketFile ticketFile : ticket.getTicketFiles()) {
			Form ticketFileForm = ticketFile.getForm();
			for (FilledForm filledForm : ticket.getFilledForms()) {
				if (filledForm.getForm().getId() == ticketFileForm.getId()) {
					// read from the file and fill the form
					PdfDocument pdf = new PdfDocument();
					pdf.loadFromBytes(ticketFile.getData());

					try {
						com.workflow.schema.Field[] fieldArray = mapper.readValue(filledForm.getFilledFormMetadata(),
								com.workflow.schema.Field[].class);
						PdfFormWidget formWidget = (PdfFormWidget) pdf.getForm();

						for (int i = 0; i < formWidget.getFieldsWidget().getCount(); i++) {
							PdfField field = (PdfField) formWidget.getFieldsWidget().getList().get(i);
							if (field instanceof PdfTextBoxFieldWidget) {
								PdfTextBoxFieldWidget textBoxField = (PdfTextBoxFieldWidget) field;
								String text = textBoxField.getText();
								for (int j = 0; j < fieldArray.length; j++) {
									if (fieldArray[j].getFieldPdf() != null
											&& fieldArray[j].getFieldPdf().equals(textBoxField.getName())) {
										fieldArray[j].setFieldValue(text);
										break;
									}
								}
							}
							if (field instanceof PdfCheckBoxWidgetFieldWidget) {
								PdfCheckBoxWidgetFieldWidget checkBoxField = (PdfCheckBoxWidgetFieldWidget) field;
								// Get the checked state of the checkbox
								boolean state = checkBoxField.getChecked();
								if(state) {
									fieldArray[0].setFieldValue(checkBoxField.getName());
								}
							}
						}

						String form_metadata = mapper.writeValueAsString(fieldArray);
						filledForm.setFilledFormMetadata(form_metadata);
						filledFormRepository.save(filledForm);

						// create file object
						File pdfFile = new File(ticketFile.getId() + ".pdf");
						pdfFile.createNewFile();
						FileOutputStream fos = new FileOutputStream(pdfFile);
						fos.write(ticketFile.getData());
						fos.close();
						logger.info("ticket id " + ticket.getId() + " - hybrid flow calling python container to find " + ticketFileForm.getSignature());
						String result = restClientUpload.exampleMultiPartUpload(pdfFile, ticketFileForm.getSignature());
						logger.info("ticket id " + ticket.getId() + " - result from python container " + result);
						formSignatureDecisionMap.put(filledForm.getForm().getFormName(),
								mapper.readValue(result, Map.class));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}

		boolean stop = true;
		while (stop) {
			Stage[] stageList;
			try {
				stageList = mapper.readValue(ticket.getWorkflow().getWorkflowMetadata(), Stage[].class);
				for (Stage stage : stageList) {
					if (ticket.getCurrentStage().equals(stage.getName())) {
						Hybrid hybrid = stage.getHybrid();
						Map<String, String> signatureDecisionMap = formSignatureDecisionMap.get(hybrid.getFormName());
						if (signatureDecisionMap != null) {
							String[] signatureList = hybrid.getSignature().split("#");
							boolean foundAllSignature = true;
							for (String signature : signatureList) {
								String result = signatureDecisionMap.get(signature);
								if (result == null) {
									foundAllSignature = false;
								} else {
									if (!result.equals("Found")) {
										foundAllSignature = false;
									}
								}
							}
							if (foundAllSignature) {
								ticketDecision(hybrid.getDecision(), id, "Hybrid");
								ticket = ticketRepository.findById(id).orElseThrow(
										() -> new ResourceNotFoundException("Ticket not exist with id :" + id));
							} else {
								stop = false;
							}
						} else {
							stop = false;
						}
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return formSignatureDecisionMap;
	}

	public void postComment(Long id, String comment, User user) {
		Ticket ticket = ticketRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Ticket not exist with id :" + id));
		TicketComment ticketComment = new TicketComment();
		ticketComment.setComment(comment);
		ticketComment.setCommentedBy(user);
		ticketComment.setCommentedOn(new Date());
		ticket.getTicketComments().add(ticketComment);
		ticketRepository.save(ticket);

	}

}
