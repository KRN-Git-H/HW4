package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.EmployeeDao;
import model.Employee;
import util.DbConnection;

public class EmployeeDaoImpl implements EmployeeDao{
	private static Connection conn=DbConnection.getDb();
	
	@Override
	public Employee login(String account, String password) {
		Employee employee = null;

		String sql="select * from employee where account=? and password=?";
		
		try {
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setString(1, account);
			ps.setString(2, password);
			ResultSet rs=ps.executeQuery();
			
			if(rs.next())
			{
				employee=new Employee();
				employee.setId(rs.getInt("id"));
				employee.setAccount(rs.getString("account"));
				employee.setPassword(rs.getString("password"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return employee;
	}
}
