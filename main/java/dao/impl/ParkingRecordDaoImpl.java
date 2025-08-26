package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import dao.ParkingRecordDao;
import model.ParkingRecord;
import util.DbConnection;


public class ParkingRecordDaoImpl implements ParkingRecordDao{

	public static void main(String[] args) {
		

		
		
		//create
//		LocalDateTime localDateTime = LocalDateTime.now();
//		LocalTime myLocalTime = LocalTime.of(10, 30, 45);
//		ParkingRecord parkingrecord = new ParkingRecord("a",localDateTime,localDateTime,myLocalTime,111);
//		new ParkingRecordDaoImpl().create(parkingrecord);
		
		//read
//		List<ParkingRecord> pr=new ParkingRecordDaoImpl().readLicense("AAA111");
////		List<ParkingRecord> pr=new ParkingRecordDaoImpl().readAll();
//		
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//		
//		for(ParkingRecord parkingrecord:pr)
//		{
//			System.out.println(parkingrecord.getLicense() + "\t" +
//							   parkingrecord.getEntryTime().format(formatter) + "\t" +
//							   parkingrecord.getExitTime().format(formatter) + "\t" +
//							   parkingrecord.getParkingTime() + "\t" +
//							   parkingrecord.getPaymentAmount() 
//			);
//		}
	}
	
	private static Connection conn=DbConnection.getDb();

	@Override
	public void create(ParkingRecord parkingrecord) {
		String sql="insert into parking_record (license,entryTime,exitTime,parkingTime,paymentAmount)" + "values(?,?,?,?,?)";

		try {
			PreparedStatement ps=conn.prepareStatement(sql);
	        
			ps.setString(1,parkingrecord.getLicense());
			ps.setObject(2,parkingrecord.getEntryTime());
			ps.setObject(3,parkingrecord.getExitTime());
			ps.setObject(4, parkingrecord.getParkingTime());
			ps.setInt(5,parkingrecord.getPaymentAmount());

			ps.executeUpdate();			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<ParkingRecord> readAllList() {
		String sql="select * from parking_record";
		
		List<ParkingRecord> parkingRecordList = new ArrayList<ParkingRecord>();
		
		try
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();

			while(rs.next())
			{
				LocalDateTime entryTime = rs.getObject("entryTime",LocalDateTime.class);
				LocalDateTime exitTime = rs.getObject("exitTime",LocalDateTime.class);
				LocalTime parkingTime =  rs.getObject("parkingTime",LocalTime.class);
				
				ParkingRecord parkingrecord = new ParkingRecord();
				
				parkingrecord.setId(rs.getInt("id"));
				parkingrecord.setLicense(rs.getString("License"));
				parkingrecord.setEntryTime(entryTime);
				parkingrecord.setExitTime(exitTime);
				parkingrecord.setParkingTime(parkingTime);
				parkingrecord.setPaymentAmount(rs.getInt("paymentAmount"));
				
				parkingRecordList.add(parkingrecord);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return parkingRecordList;
	}
	
	@Override
	public List<ParkingRecord> readLicenseList(String license) {
		String sql="select * from parking_record where license=?";
		
		List<ParkingRecord> parkingRecordList = new ArrayList<ParkingRecord>();
		
		try
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, license);
			ResultSet rs=ps.executeQuery();

			while(rs.next())
			{
				LocalDateTime entryTime = rs.getObject("entryTime",LocalDateTime.class);
				LocalDateTime exitTime = rs.getObject("exitTime",LocalDateTime.class);
				LocalTime parkingTime =  rs.getObject("parkingTime",LocalTime.class);
				
				ParkingRecord parkingrecord = new ParkingRecord();
				
				parkingrecord.setId(rs.getInt("id"));
				parkingrecord.setLicense(rs.getString("License"));
				parkingrecord.setEntryTime(entryTime);
				parkingrecord.setExitTime(exitTime);
				parkingrecord.setParkingTime(parkingTime);
				parkingrecord.setPaymentAmount(rs.getInt("paymentAmount"));
				
				parkingRecordList.add(parkingrecord);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return parkingRecordList;
	}
}
