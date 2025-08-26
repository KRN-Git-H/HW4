package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import dao.MonthRentDao;
import model.MonthRent;
import util.DbConnection;

public class MonthRentDaoImpl implements MonthRentDao{

	public static void main(String[] args) {
		//create
//        LocalDateTime localDateTime = LocalDateTime.now();
//		MonthRent monthrent = new MonthRent("a",localDateTime,localDateTime,111,"d");
//		new MonthRentDaoImpl().create(monthrent);
//		
		//read
//		System.out.println(new MonthRentDaoImpl().readLicense("EEE555").getId());
//		System.out.println(new MonthRentDaoImpl().selectMethod("月繳"));
	//	System.out.println(new MonthRentDaoImpl().read("AAA111"));
		
//		List<MonthRent> mr=new MonthRentDaoImpl().readMethod("月繳");
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//		
//		for(MonthRent monthrent:mr)
//		{
//				System.out.println(monthrent.getLicense() + "\t" + 
//								   monthrent.getPaymentTime().format(formatter) + "\t" + 
//								   monthrent.getExpiryTime().format(formatter) + "\t" + 
//								   monthrent.getPaymentAmount() + "\t" +
//								   monthrent.getPaymentMethod()
//								   );
//		}

		//update
//		MonthRent monthrent=new MonthRentDaoImpl().readLicense("eee555");
//		monthrent.setLicense("EEE554");
//		new MonthRentDaoImpl().update(monthrent);
		
		//delete
//		new MonthRentDaoImpl().delete(new MonthRentDaoImpl().readLicense("a1"));

	}

	
	private static Connection conn=DbConnection.getDb();

	@Override
	public void create(MonthRent monthrent) {
		String sql="insert into month_Rent(license,paymentTime,expiryTime,paymentAmount,paymentMethod) " + "values(?,?,?,?,?)";

		try {
			PreparedStatement ps=conn.prepareStatement(sql);

	        Timestamp paymenTime = Timestamp.valueOf(monthrent.getPaymentTime());
	        Timestamp expiryTime = Timestamp.valueOf(monthrent.getExpiryTime());
			
			ps.setString(1, monthrent.getLicense());
			ps.setTimestamp(2,paymenTime);
			ps.setTimestamp(3,expiryTime);
			ps.setInt(4, monthrent.getPaymentAmount());
			ps.setString(5, monthrent.getPaymentMethod());

			ps.executeUpdate();			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<MonthRent> readAllList() {
		String sql="select * from month_rent";
		
		List<MonthRent> monthRentList = new ArrayList();
		
		try
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();

			while(rs.next())
			{
				LocalDateTime paymenTtime = rs.getTimestamp("paymenTtime").toLocalDateTime();
				LocalDateTime expiryTime = rs.getTimestamp("expiryTime").toLocalDateTime();
				
				MonthRent monthrent = new MonthRent();
				
				monthrent.setId(rs.getInt("id"));
				monthrent.setLicense(rs.getString("License"));
				monthrent.setPaymentTime(paymenTtime);
				monthrent.setExpiryTime(expiryTime);
				monthrent.setPaymentAmount(rs.getInt("paymentAmount"));
				monthrent.setPaymentMethod(rs.getString("paymentMethod"));
				
				monthRentList.add(monthrent);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return monthRentList;
	}
	
	@Override
	public List<MonthRent> readLicenseList(String license) {
		String sql="select * from month_rent where license=?";
		
		List<MonthRent> monthRentList = new ArrayList();
		
		try
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, license);
			ResultSet rs=ps.executeQuery();

			while(rs.next())
			{
				LocalDateTime paymenTtime = rs.getTimestamp("paymenTtime").toLocalDateTime();
				LocalDateTime expiryTime = rs.getTimestamp("expiryTime").toLocalDateTime();
				
				MonthRent monthrent = new MonthRent();
				
				monthrent.setId(rs.getInt("id"));
				monthrent.setLicense(rs.getString("License"));
				monthrent.setPaymentTime(paymenTtime);
				monthrent.setExpiryTime(expiryTime);
				monthrent.setPaymentAmount(rs.getInt("paymentAmount"));
				monthrent.setPaymentMethod(rs.getString("paymentMethod"));
				
				monthRentList.add(monthrent);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return monthRentList;
	}
	
	@Override
	public MonthRent readLicense(String license) {
		MonthRent monthrent = null;

		String sql="select * from month_rent where license=?";
		
		try {
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setString(1, license);
			ResultSet rs=ps.executeQuery();
			
			if(rs.next())
			{
				LocalDateTime paymenTtime = rs.getTimestamp("paymenTtime").toLocalDateTime();
				LocalDateTime expiryTime = rs.getTimestamp("expiryTime").toLocalDateTime();
				
				monthrent=new MonthRent();
				monthrent.setId(rs.getInt("id"));
				monthrent.setLicense(rs.getString("license"));
				monthrent.setPaymentTime(paymenTtime);
				monthrent.setExpiryTime(expiryTime);
				monthrent.setPaymentAmount(rs.getInt("paymentAmount"));
				monthrent.setPaymentMethod(rs.getString("paymentMethod"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return monthrent;
	}


	@Override
	public List<MonthRent> readMethod(String method) {
		String sql="select * from month_rent where Method=?";
		
		List<MonthRent> monthRentList = new ArrayList();
		
		try
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, method);
			ResultSet rs=ps.executeQuery();

			while(rs.next())
			{
				LocalDateTime paymenTtime = rs.getTimestamp("paymenTtime").toLocalDateTime();
				LocalDateTime expiryTime = rs.getTimestamp("expiryTime").toLocalDateTime();
				
				MonthRent monthrent = new MonthRent();
				
				monthrent.setId(rs.getInt("id"));
				monthrent.setLicense(rs.getString("License"));
				monthrent.setPaymentTime(paymenTtime);
				monthrent.setExpiryTime(expiryTime);
				monthrent.setPaymentAmount(rs.getInt("paymentAmount"));
				monthrent.setPaymentMethod(rs.getString("paymentMethod"));
				
				monthRentList.add(monthrent);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return monthRentList;
	}

	@Override
	public void update(MonthRent monthrent) {
		String sql="update month_rent set license=?,paymentTime=?,expiryTime=?,paymentAmount=?,paymentMethod=? where id=?";
		
		try {
			PreparedStatement ps=conn.prepareStatement(sql);
			
	        Timestamp paymenTime = Timestamp.valueOf(monthrent.getPaymentTime());
	        Timestamp expiryTime = Timestamp.valueOf(monthrent.getExpiryTime());
			
			ps.setString(1, monthrent.getLicense());
			ps.setTimestamp(2, paymenTime);
			ps.setTimestamp(3, expiryTime);
			ps.setInt(4, monthrent.getPaymentAmount());
			ps.setString(5, monthrent.getPaymentMethod());
			ps.setInt(6, monthrent.getId());
			
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(MonthRent monthrent) {
		String sql="delete from month_rent where id=?";

		try {
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setInt(1, monthrent.getId());
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
