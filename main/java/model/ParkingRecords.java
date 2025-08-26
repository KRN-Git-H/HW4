package model;

import java.time.LocalDateTime;

public class ParkingRecords {
	private String license;
	private LocalDateTime entryTime;
	private LocalDateTime exitTime;
	private LocalDateTime parkingTime;
	private int paymentAmount;
	
	public ParkingRecords() {
			
	}
	
	public ParkingRecords(String license,LocalDateTime entryTime,LocalDateTime exitTime,LocalDateTime parkingTime,int paymentAmount) {
		this.license = license;
		this.entryTime = entryTime;
		this.exitTime = exitTime;
		this.parkingTime = parkingTime;
		this.paymentAmount = paymentAmount;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public LocalDateTime getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(LocalDateTime entryTime) {
		this.entryTime = entryTime;
	}

	public LocalDateTime getExitTime() {
		return exitTime;
	}

	public void setExitTime(LocalDateTime exitTime) {
		this.exitTime = exitTime;
	}

	public LocalDateTime getParkingTime() {
		return parkingTime;
	}

	public void setParkingTime(LocalDateTime parkingTime) {
		this.parkingTime = parkingTime;
	}

	public int getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(int paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	
	
	
	
	
	
	
	
	
	
	
}
