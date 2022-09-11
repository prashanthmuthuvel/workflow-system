package com.workflow.schema;

import java.util.List;

public class Field {

	private String fieldId;
	private String fieldLabel;
	private String fieldValue;
	private String fieldMandatory;
	private List<FieldOption> fieldOptions = null;
	private String fieldType;
	private String fieldPlaceholder;
	private String fieldPdf;
	
	public Field() {
		
	}

	public Field(String fieldId, String fieldLabel, String fieldValue, String fieldMandatory,
			List<FieldOption> fieldOptions, String fieldType, String fieldPlaceholder, String fieldPdf) {
		super();
		this.fieldId = fieldId;
		this.fieldLabel = fieldLabel;
		this.fieldValue = fieldValue;
		this.fieldMandatory = fieldMandatory;
		this.fieldOptions = fieldOptions;
		this.fieldType = fieldType;
		this.fieldPlaceholder = fieldPlaceholder;
		this.fieldPdf = fieldPdf;
	}

	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	public String getFieldLabel() {
		return fieldLabel;
	}

	public void setFieldLabel(String fieldLabel) {
		this.fieldLabel = fieldLabel;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public String getFieldMandatory() {
		return fieldMandatory;
	}

	public void setFieldMandatory(String fieldMandatory) {
		this.fieldMandatory = fieldMandatory;
	}

	public List<FieldOption> getFieldOptions() {
		return fieldOptions;
	}

	public void setFieldOptions(List<FieldOption> fieldOptions) {
		this.fieldOptions = fieldOptions;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getFieldPlaceholder() {
		return fieldPlaceholder;
	}

	public void setFieldPlaceholder(String fieldPlaceholder) {
		this.fieldPlaceholder = fieldPlaceholder;
	}

	public String getFieldPdf() {
		return fieldPdf;
	}

	public void setFieldPdf(String fieldPdf) {
		this.fieldPdf = fieldPdf;
	}

}
