package service.impl;

import java.util.List;

import controller.admin.MonthRentModifyUI;
import dao.impl.MonthRentDaoImpl;
import model.MonthRent;
import service.MonthRentService;
import util.Tool;

public class MonthRentServiceImpl implements MonthRentService{

	public static void main(String[] args) {
		//create
//        LocalDateTime localDateTime = LocalDateTime.now();
//		MonthRent monthrent = new MonthRent("bbbb",localDateTime,localDateTime,111,"d");
//		new MonthRentServiceImpl().create(monthrent);
		
		//update
//		MonthRent monthrent=new MonthRentDaoImpl().readLicense("EEE554");
//		monthrent.setLicense("EEE551");
//		new MonthRentServiceImpl().update(monthrent);
		
		
		//delete
//		MonthRent monthrent = new MonthRentDaoImpl().readLicense("a");
//		System.out.print(monthrent.getId());
//		new MonthRentDaoImpl().delete(monthrent);
		
		
//		MonthRent monthrent = new MonthRentDaoImpl().readLicense("a");
//		System.out.print(monthrent.getId());
//		new MonthRentServiceImpl().delete(monthrent);
		

		
		
	}
	
	private static MonthRentDaoImpl monthRentDI=new MonthRentDaoImpl();
	
	@Override
	public boolean create(MonthRent monthRent) 
	{	boolean result = false;

		try
		{
			MonthRent license = monthRentDI.readLicense(monthRent.getLicense());
			result = license==null ? true : false;
	
			if(result)
			{
				monthRentDI.create(monthRent);
				Tool.alarmMsg(MonthRentModifyUI.getFrames()[0],"月租資料新增成功。");
			}
			else
			{
				Tool.alarmMsg(MonthRentModifyUI.getFrames()[0],"月租資料新增失敗。");
			}
		}
		catch (Exception E)
		{
			System.out.println("MonthRentSI.create Error");
		}
		
		return result;
	}

	@Override
	public boolean readLicense(String license) {
		return monthRentDI.readLicense(license)==null ? false : true;
	}

	@Override
	public List<MonthRent> readAll() {
		return monthRentDI.readAllList();
	}
	
	@Override
	public List<MonthRent> readMethod(String paymentmethod) {
		return monthRentDI.readMethod(paymentmethod);
	}
	

	@Override
	public boolean update(MonthRent monthRent) {
		boolean result = false;
		
		try
		{
			MonthRent license = monthRentDI.readLicense(monthRent.getLicense());
			result = license!=null && Tool.checkMemberRentFormat(monthRent) ? true : false;
			
			if(result)
			{
				monthRentDI.update(monthRent);
				Tool.alarmMsg(MonthRentModifyUI.getFrames()[0],"月租資料修改成功。");
			}
			else
			{
				Tool.alarmMsg(MonthRentModifyUI.getFrames()[0],"月租資料修改失敗。");
			}
		}
		catch (Exception E)
		{
			System.out.println("MonthRentSI.update Error");
		}
		
		return result;
	}

	@Override
	public boolean delete(MonthRent monthRent) {
		boolean result = false;
		
		try
		{
			MonthRent license = monthRentDI.readLicense(monthRent.getLicense());
			result = license!=null && Tool.checkMemberRentFormat(monthRent) ? true : false;
			
			if(result)
			{
				monthRentDI.delete(license);
				Tool.alarmMsg(MonthRentModifyUI.getFrames()[0],"月租資料刪除成功。");
			}
			else
			{
				Tool.alarmMsg(MonthRentModifyUI.getFrames()[0],"月租資料刪除失敗。");
			}
		}
		catch(Exception E)
		{
			System.out.println("MonthRentSI.delete Error");
		}
		
		return result;
	}





}
