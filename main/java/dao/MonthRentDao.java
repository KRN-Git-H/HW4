package dao;

import java.util.List;

import model.MonthRent;

public interface MonthRentDao {
	//create
	void create(MonthRent monthrent);
	
	//read
	MonthRent readLicense(String license);
	List<MonthRent> readAllList();	
	List<MonthRent> readLicenseList(String license);
	List<MonthRent> readMethod(String Method);

	
	//update
	void update(MonthRent monthrent);
	
	//delete
	void delete(MonthRent monthrent);

}
