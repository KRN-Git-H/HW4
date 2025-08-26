package service;

import model.Employee;

public interface EmployeeService {
	//read
	Employee login(String account,String password);
	
}
