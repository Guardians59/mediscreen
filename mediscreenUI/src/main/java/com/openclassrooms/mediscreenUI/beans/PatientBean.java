package com.openclassrooms.mediscreenUI.beans;

public class PatientBean {
    
    private int id;
    private String firstName;
    private String lastName;
    private String birthday;
    private String gender;
    private String address;
    private String phoneNumber;
   
    public PatientBean() {
	
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
	return "PatientBeans [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", birthday="
		+ birthday + ", gender=" + gender + ", address=" + address + ", phoneNumber=" + phoneNumber + "]";
    }

}
