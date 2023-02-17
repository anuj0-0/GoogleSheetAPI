package com.ty.sheets.SheetsAPI.entity;

public class Student {

	private String name;
	private String age;
	private String phone;
	private String bloodGroup;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	@Override
	public String toString() {
		return "Student [name=" + name + ", age=" + age + ", phone=" + phone + ", bloodGroup=" + bloodGroup + "]";
	}

}
