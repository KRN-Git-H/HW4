package service.impl;

import dao.impl.EmployeeDaoImpl;
import model.Employee;
import service.EmployeeService;

public class EmployeeServiceImpl implements EmployeeService{
	private static EmployeeDaoImpl employeeDI = new EmployeeDaoImpl();	

	
	@Override
	public Employee login(String account, String password) {
		return employeeDI.login(account, password);
	}

}
