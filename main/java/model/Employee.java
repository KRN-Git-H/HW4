package model;

import java.io.Serializable;

public class Employee implements Serializable{
	private int id;
	private String account;
	private String password;
	private String employeeNumber;
	private String category;
	
	public Employee()
	{
		
	}
	
	public Employee(String account,String password,String employeeNumber)
	{
		this.account = account;
		this.password = password;		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}
