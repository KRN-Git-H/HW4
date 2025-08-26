package service;

import java.util.List;

import model.MonthRent;

public interface MonthRentService {
	//create
	boolean create(MonthRent monthrent);

	//read
	boolean readLicense(String license);
	List<MonthRent> readAll();
	List<MonthRent> readMethod(String paymentmethod);
	
	
	//update
	boolean update(MonthRent monthrent);
	
	//delete
	boolean delete(MonthRent monthrent);
}
