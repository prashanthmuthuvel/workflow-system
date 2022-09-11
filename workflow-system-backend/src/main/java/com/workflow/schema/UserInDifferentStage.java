package com.workflow.schema;

public class UserInDifferentStage {

	private String professor;
	private String student;
	private String chair;

	public UserInDifferentStage() {

	}

	public UserInDifferentStage(String professor, String student, String chair) {
		super();
		this.professor = professor;
		this.student = student;
		this.chair = chair;
	}

	public String getProfessor() {
		return professor;
	}

	public void setProfessor(String professor) {
		this.professor = professor;
	}

	public String getStudent() {
		return student;
	}

	public void setStudent(String student) {
		this.student = student;
	}

	public String getChair() {
		return chair;
	}

	public void setChair(String chair) {
		this.chair = chair;
	}

}
