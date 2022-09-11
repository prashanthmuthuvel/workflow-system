package com.workflow.schema;

public class FormFileInfo {
	
	private String formName;
	private String fileId;
	
	public FormFileInfo() {
		
	}

	public FormFileInfo(String formName, String fileId) {
		super();
		this.formName = formName;
		this.fileId = fileId;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	
	

}
