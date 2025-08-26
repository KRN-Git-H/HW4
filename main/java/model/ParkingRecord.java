package model;


import java.time.LocalDateTime;
import java.time.LocalTime;

public class ParkingRecord {
	private int id;
	private String license;
	private LocalDateTime entryTime;
	private LocalDateTime exitTime;
	private LocalTime parkingTime;
	private int paymentAmount;
	
	public ParkingRecord() {
			
	}
	
	public ParkingRecord(String license,LocalDateTime entryTime,LocalDateTime exitTime,LocalTime parkingTime,int paymentAmount) {
		this.license = license;
		this.entryTime = entryTime;
		this.exitTime = exitTime;
		this.parkingTime = parkingTime;
		this.paymentAmount = paymentAmount;
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

	public LocalTime getParkingTime() {
		return parkingTime;
	}

	public void setParkingTime(LocalTime parkingTime) {
		this.parkingTime = parkingTime;
	}

	public int getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(int paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	
	
	
	
	
	
	
	
	
	
	
}
