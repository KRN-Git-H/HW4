package service.impl;

import java.util.List;

import javax.swing.JOptionPane;

import controller.admin.MemberModifyUI;
import controller.admin.MonthRentModifyUI;
import dao.impl.MemberDaoImpl;
import dao.impl.MemberLicenseDaoImpl;
import dao.impl.MonthRentDaoImpl;
import model.Member;
import model.MemberLicense;
import model.MonthRent;
import service.MemberService;
import util.Tool;

public class MemberServiceImpl implements MemberService {

	public static void main(String[] args) {
		//read
//		System.out.println(new MemberServiceImpl().readAmount("JJJ3d33"));

//		//delete
//		new MemberServiceImpl().delete(new MemberDaoImpl().read("aaa"));
	}

	private static MemberDaoImpl memberDI = new MemberDaoImpl();	
	private static MonthRentDaoImpl monthrentDI = new MonthRentDaoImpl();
	private static MemberLicenseDaoImpl memberLicenseDI = new MemberLicenseDaoImpl();
	
	@Override
	public boolean create(Member member) {
		boolean result = false;
		
		if(Tool.checkMemberFormat(member))
		{
			try
			{
				Member account = memberDI.readAccount(member.getAccount());
				result = account ==null ? true : false;
		
				if(result)
				{
					memberDI.create(member);
					Tool.alarmMsg(MemberModifyUI.getFrames()[0],"會員資料新增成功。");
				}
				else
				{
					Tool.alarmMsg(MemberModifyUI.getFrames()[0],"會員資料重覆。");
				}
			}
			catch (Exception E)
			{
				System.out.println("MemberSI.create Error");
			}
		}
		else
		{
			Tool.alarmMsg(MemberModifyUI.getFrames()[0],"會員資料不完整，新增失敗。");
		}
		
		return result;
	}	
	
	@Override
	public int readAmount(String license) {
		String category="";
		int charge=0;
		
		MemberLicense memberlicense= memberLicenseDI.readLicense(license);
		
		Member member = memberDI.readAccount(memberlicense.getAccount());
		MonthRent monthrent = monthrentDI.readLicense(license);
		
		try 
		{
			category = member.getCategory();
			charge = monthrent.fee;
		
			if (category.equals("身障"))
			{
				charge*=0.5;
			}
			else if(category.equals("里民"))
			{
				charge*=0.8;
			}			
		}
		catch (Exception e)
		{
			Tool.alarmMsg(MonthRentModifyUI.getFrames()[0],"無此車號，請先加入會員登錄車號。");
		}

		return charge;
	}
	
	@Override
	public Member readAccount(String account) {
		return memberDI.readAccount(account);
	}

	@Override
	public Member login(String account, String password) {
		return memberDI.login(account, password);
	}
	
	@Override
	public List<Member> readAll() {
		return memberDI.readAllList();
	}
	
	@Override
	public boolean readLicenseList(String license) {
		return memberDI.readLicenseList(license).isEmpty() ? false : true;
	}

	@Override
	public boolean update(Member member) {
		boolean result;
		
		Member account = memberDI.readAccount(member.getAccount());
		result = account !=null ? true : false;
		
		if(Tool.checkMemberFormat(member))
		{
			
			if (account.equals(member))
			{
				Tool.alarmMsg(MemberModifyUI.getFrames()[0],"會員資料無異動。");
			}
			else
			{
				try
				{
					if(result)
					{
						member.setId(account.getId());
						memberDI.update(member);
						Tool.alarmMsg(MemberModifyUI.getFrames()[0],"會員資料修改成功。");
					}
						else
					{
						Tool.alarmMsg(MemberModifyUI.getFrames()[0],"會員資料修改成功。");
					}
				}
				catch(Exception E)
				{
					System.out.println("MemberSI.update Error");
				}
			}
		}
		else
		{
			Tool.alarmMsg(MemberModifyUI.getFrames()[0],"會員資料不完整，修改失敗。");
		}
		
		return result;
	}

	@Override
	public boolean delete(Member member) {
		boolean result = false;
		
		Member account = memberDI.readAccount(member.getAccount());
		result = account !=null ? true : false;
		
		try
		{
			if(result)
			{
				member.setId(account.getId());
				memberDI.delete(member);
				Tool.alarmMsg(MonthRentModifyUI.getFrames()[0],"帳號刪除成功。");
			}
			else
			{
				Tool.alarmMsg(MonthRentModifyUI.getFrames()[0],"帳號刪除失敗。");
			}
		}
		catch (Exception E)
		{
			System.out.println("Member.delete Error");
		}
		
		return result;
	}
}
