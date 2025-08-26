package dao;

import model.Employee;

public interface EmployeeDao {
	
	//read
	Employee login(String account,String password);

}
