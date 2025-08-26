package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dao.MemberLicenseDao;
import model.MemberLicense;
import util.DbConnection;

public class MemberLicenseDaoImpl implements MemberLicenseDao{
	public static void main(String[] args) {
		//create
//		MemberLicense memberlicense = new MemberLicense("a","b");
//		new MemberLicenseDaoImpl().create(memberlicense);
	
		//read
//		System.out.println(new MemberLicenseDaoImpl().readLicense("b"));
		
		//read
//		List<MemberLicense> memberLicenseList=new MemberLicenseDaoImpl().readAll();
//		
//		for(MemberLicense memberlicenselist:memberLicenseList)
//		{
//				System.out.println(memberlicenselist.getAccount() + "\t" + 
//							       memberlicenselist.getLicense() + "\t"
//								   );
//		}
		
		//update
//		MemberLicense memberlicense = new MemberLicense("aa","ab");
//		memberlicense.setId(1);
//		new MemberLicenseDaoImpl().update(memberlicense);
		
		//delete
//		new MemberLicenseDaoImpl().delete(new MemberLicenseDaoImpl().readLicense("d"));
	}

	private static Connection conn=DbConnection.getDb();
	
	public void create(MemberLicense memberlicense) {
		String sql="insert into member_license(account,license)" + "values(?,?)";

		try 
		{
			PreparedStatement ps=conn.prepareStatement(sql);
			
			ps.setString(1, memberlicense.getAccount());
			ps.setString(2, memberlicense.getLicense());
			ps.executeUpdate();			
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}

	@Override
	public MemberLicense readLicense(String license) {
		MemberLicense memberLicense = null;

		String sql="select * from member_license where license=?";
		
		try {
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setString(1, license);
			ResultSet rs=ps.executeQuery();
			
			if(rs.next())
			{
				memberLicense=new MemberLicense();
				
				memberLicense.setId(rs.getInt("id"));
				memberLicense.setAccount(rs.getString("account"));
				memberLicense.setLicense(rs.getString("license"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return memberLicense;
	}
	
	@Override
	public List<MemberLicense> readAllList() {
		String sql="select * from member_license";
		
		List<MemberLicense> emberLicenseList = new ArrayList<MemberLicense>();
		
		try
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();

			while(rs.next())
			{
				MemberLicense memberlicense = new MemberLicense();

				memberlicense.setAccount(rs.getString("account"));
				memberlicense.setLicense(rs.getString("license"));
				
				emberLicenseList.add(memberlicense);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return emberLicenseList;
	}
	
	//不重覆的帳號
	public List<MemberLicense> readAllAccountList() {
		String sql="select * from member_license";
		
		List<MemberLicense> emberLicenseList = new ArrayList<MemberLicense>();
		Set<String> seenAccounts = new HashSet<>();  // 用來追蹤已處理過的 account
		
		try
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();

			while(rs.next())
			{
	            String account = rs.getString("account");
	
	            // 如果這個 account 沒有被處理過，才加入到結果列表中
	            if (!seenAccounts.contains(account)) {
	                MemberLicense memberLicense = new MemberLicense();
	
	                memberLicense.setAccount(account);
	                memberLicense.setLicense(rs.getString("license"));
	
	                emberLicenseList.add(memberLicense);
	                seenAccounts.add(account);  // 標記這個 account 為已處理過
	            }
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return emberLicenseList;
	}
	
	@Override
	public List<MemberLicense> readAccountList(String account) {
		String sql="select * from member_license where account=?";
		
		List<MemberLicense> memberAccountList = new ArrayList();
		
		try
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, account);
			ResultSet rs=ps.executeQuery();

			while(rs.next())
			{
				MemberLicense memberLicense = new MemberLicense();
				
				memberLicense.setAccount(rs.getString("account"));
				memberLicense.setLicense(rs.getString("license"));

				memberAccountList.add(memberLicense);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return memberAccountList;
	}

	@Override
	public List<MemberLicense> readLicenseList(String license) {
		String sql="select * from member_license where license=?";
		
		List<MemberLicense> memberLicenseList = new ArrayList();
		
		try
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, license);
			ResultSet rs=ps.executeQuery();

			while(rs.next())
			{
				MemberLicense memberLicense = new MemberLicense();
				
				memberLicense.setAccount(rs.getString("account"));
				memberLicense.setLicense(rs.getString("license"));

				memberLicenseList.add(memberLicense);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return memberLicenseList;
	}
	
	@Override
	public void update(MemberLicense memberLicense) {
		String sql="update member_license set account=?,license=? where id=?";
		
		try {
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setString(1, memberLicense.getAccount());
			ps.setString(2, memberLicense.getLicense());
			ps.setInt(3, memberLicense.getId());
			
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void delete(MemberLicense memberLicense) {
		String sql="delete from member_license where id=?";
		
		try {
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setInt(1, memberLicense.getId());
			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
