package com.openclassrooms.msreport.model;

public class Report {
    
    private String firstName;
    private String lastName;
    private int age;
    private String diabetesAssessment;
    
    public Report() {
	
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDiabetesAssessment() {
        return diabetesAssessment;
    }

    public void setDiabetesAssessment(String diabetesAssessment) {
        this.diabetesAssessment = diabetesAssessment;
    }

    @Override
    public String toString() {
	return "Report [firstName=" + firstName + ", lastName=" + lastName + ", age=" + age + ", diabetesAssessment="
		+ diabetesAssessment + "]";
    }

}
