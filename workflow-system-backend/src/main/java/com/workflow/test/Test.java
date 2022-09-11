package com.workflow.test;

import com.spire.pdf.PdfDocument;
import com.spire.pdf.widget.PdfFormWidget;
import com.spire.pdf.widget.PdfTextBoxFieldWidget;

import com.spire.pdf.fields.PdfField;
import com.spire.pdf.widget.*;

import java.io.FileWriter;
import java.io.IOException;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("I am the test method here");
		// Load PDF document
		PdfDocument pdf = new PdfDocument();
		pdf.loadFromFile("test.pdf");

		PdfFormWidget formWidget = (PdfFormWidget) pdf.getForm();

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < formWidget.getFieldsWidget().getCount(); i++) {
			PdfField field = (PdfField) formWidget.getFieldsWidget().getList().get(i);
			if (field instanceof PdfTextBoxFieldWidget) {
				PdfTextBoxFieldWidget textBoxField = (PdfTextBoxFieldWidget) field;
				String text = textBoxField.getText();
				sb.append("The textbox is: " + textBoxField.getName() + " ");
				sb.append("The text in textbox is: " + text + "\r\n");
			}

			if (field instanceof PdfListBoxWidgetFieldWidget) {
				PdfListBoxWidgetFieldWidget listBoxField = (PdfListBoxWidgetFieldWidget) field;
				sb.append("Listbox items are: \r\n");
				// Get values of listbox
				PdfListWidgetItemCollection items = listBoxField.getValues();
				for (PdfListWidgetItem item : (Iterable<PdfListWidgetItem>) items) {
					sb.append(item.getValue() + "\r\n");
				}
				String selectedValue = listBoxField.getSelectedValue();
				sb.append("The selected value in the listbox is: " + selectedValue + "\r\n");
			}
			if (field instanceof PdfComboBoxWidgetFieldWidget) {
				PdfComboBoxWidgetFieldWidget comBoxField = (PdfComboBoxWidgetFieldWidget) field;
				sb.append("comBoxField items are: \r\n");
				PdfListWidgetItemCollection items = comBoxField.getValues();
				for (PdfListWidgetItem item : (Iterable<PdfListWidgetItem>) items) {
					sb.append(item.getValue() + "\r\n");
				}
				String selectedValue = comBoxField.getSelectedValue();
				sb.append("The selected value in the comBoxField is: " + selectedValue + "\r\n");
			}
			if (field instanceof PdfRadioButtonListFieldWidget) {
				PdfRadioButtonListFieldWidget radioBtnField = (PdfRadioButtonListFieldWidget) field;
				// Get value of radio button
				String value = radioBtnField.getValue();
				sb.append("The text in radioButtonField is: " + value + "\r\n");
			}
			if (field instanceof PdfCheckBoxWidgetFieldWidget) {
				PdfCheckBoxWidgetFieldWidget checkBoxField = (PdfCheckBoxWidgetFieldWidget) field;
				// Get the checked state of the checkbox
				boolean state = checkBoxField.getChecked();
				sb.append("checkbox filed : " + checkBoxField.getName());
				sb.append("Is the checkBox checked? " + state + "\r\n");
			}
		}
		System.out.println(sb.toString());
		pdf.close();

	}

}
