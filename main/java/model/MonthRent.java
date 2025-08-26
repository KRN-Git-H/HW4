package model;

import java.time.LocalDateTime;

public class MonthRent implements LicenseHolder{
	private int id;
	private String license;
	private LocalDateTime paymentTime;
	private LocalDateTime expiryTime;
	private int paymentAmount;
	private String paymentMethod;
	public static int fee = 5000;
	
	public MonthRent() {
		
	}
	
	public MonthRent(String license,LocalDateTime paymentTime,LocalDateTime expiryTime,int paymentAmount,String paymentMethod) {
		this.license = license;
		this.paymentTime = paymentTime;
		this.expiryTime = expiryTime;
		this.paymentAmount = paymentAmount;
		this.paymentMethod = paymentMethod;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public LocalDateTime getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(LocalDateTime paymentTime) {
		this.paymentTime = paymentTime;
	}

	public LocalDateTime getExpiryTime() {
		return expiryTime;
	}

	public void setExpiryTime(LocalDateTime expiryTime) {
		this.expiryTime = expiryTime;
	}
	
	
	public int getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(int paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	
}
