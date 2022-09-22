package com.openclassrooms.mediscreenUI.models;

public class GetPatientModel {
    
    private String firstName;
    private String lastName;
    private String birthday;
    
    public GetPatientModel() {
	
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
    public String getBirthday() {
        return birthday;
    }
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    @Override
    public String toString() {
	return "GetPatientModel [firstName=" + firstName + ", lastName=" + lastName + ", birthday=" + birthday + "]";
    }

}
