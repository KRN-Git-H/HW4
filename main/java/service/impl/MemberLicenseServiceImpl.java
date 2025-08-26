package service.impl;

import java.util.List;

import controller.admin.MemberModifyUI;
import controller.admin.MonthRentModifyUI;
import dao.impl.MemberLicenseDaoImpl;
import model.Member;
import model.MemberLicense;
import service.MemberLicenseService;
import util.Tool;
import util.ToolUI;

public class MemberLicenseServiceImpl implements MemberLicenseService{
	public static void main(String[] args) {
		
		
		
	}
	
	private static MemberLicenseDaoImpl memberLicenseDI = new MemberLicenseDaoImpl();	

	@Override
	public boolean create(MemberLicense memberLicense) {
		MemberLicense license = memberLicenseDI.readLicense(memberLicense.getLicense());

		boolean result = license == null && !memberLicense.getLicense().equals("") ? true : false;

		try
		{
			if(result)
			{
				memberLicenseDI.create(memberLicense);
				Tool.alarmMsg(MemberModifyUI.getFrames()[0],"車牌資料新增成功。");
			}
			else
			{
				Tool.alarmMsg(MemberModifyUI.getFrames()[0],"車牌資料新增失敗。");
			}
		}
		catch(Exception E)
		{
			System.out.println("MemberLicenseSI.create Error");
		}
		
		return result;
	}

	@Override
	public MemberLicense readLicense(String license) {
		return memberLicenseDI.readLicense(license);
	}

	@Override
	public List<MemberLicense> readAll() {
		return memberLicenseDI.readAllList();
	}

	@Override
	public List<MemberLicense> readAccountList(String account) {
		return memberLicenseDI.readAccountList(account);
	}

	@Override
	public List<MemberLicense> readLicenseList(String license) {
		return memberLicenseDI.readLicenseList(license);
	}

	@Override
	public boolean update(MemberLicense memberLicense) {
		boolean result = false;
		
		String[] licenseStr = memberLicense.getLicense().split(">");

		MemberLicense license = memberLicenseDI.readLicense(licenseStr[0]);
		
		result = license !=null ? true : false;
		
		try
		{
			if(result)
			{
				license.setLicense(licenseStr[1]);
				memberLicenseDI.update(license);
				Tool.alarmMsg(MemberModifyUI.getFrames()[0],"車牌資料修改成功。");
			}
			else
			{
				Tool.alarmMsg(MemberModifyUI.getFrames()[0],"車牌資料修改失敗。");
			}
		}
		catch (Exception E)
		{
			System.out.println("MemberLicenseSI.update Error");
		}

		return result;
	}

	@Override
	public boolean delete(MemberLicense memberLicense) {
		boolean result = false;
		MemberLicense license = memberLicenseDI.readLicense(memberLicense.getLicense());
		
		result = license !=null ? true : false;
		
		try
		{
			if(result)
			{
				memberLicenseDI.delete(license);
				Tool.alarmMsg(MemberModifyUI.getFrames()[0],license.getLicense() + " 車牌資料刪除成功。");
			}
			else
			{
				Tool.alarmMsg(MemberModifyUI.getFrames()[0],license.getLicense() + " 車牌資料刪除失敗。");
			}
		}
		catch(Exception E)
		{
			System.out.println("MemberLicenseSI.delete Error");
		}

		return result;
	}

}
