package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import dao.MemberDao;
import model.Member;
import model.MonthRent;
import util.DbConnection;


public class MemberDaoImpl implements MemberDao  {

	public static void main(String[] args) {
		//create
//		Member member = new Member("a","b","c","d","e","f");
//		new MemberDaoImpl().create(member);
		
		//read
//		System.out.println(new MemberDaoImpl().readLicense("AAA1211"));
		
//		List<Member> memberList=new MemberDaoImpl().readAll();
//		
//		for(Member member:memberList)
//		{
//				System.out.println(member.getAccount() + "\t" + 
//						member.getName() + "\t" + 
//						member.getPhone() + "\t" + 
//						member.getLicense() + "\t" +
//								   member.getCategory()
//								   );
//		}
		
		
		//update
//		Member member = new Member("a","a","a","a","a","a");
//		member.setId(7);
//		new MemberDaoImpl().update(member);
		
		//delete
//		new MemberDaoImpl().delete(new MemberDaoImpl().read("aaa"));
		
	}
	
	private static Connection conn=DbConnection.getDb();
	
	public void create(Member member) {
		String sql="insert into member(account,password,name,phone,license,category) " + "values(?,?,?,?,?,?)";

		try {
			PreparedStatement ps=conn.prepareStatement(sql);
			
			ps.setString(1, member.getAccount());
			ps.setString(2, member.getPassword());
			ps.setString(3, member.getName());
			ps.setString(4, member.getPhone());
			ps.setString(5, member.getLicense());
			ps.setString(6, member.getCategory());	
			ps.executeUpdate();			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Member readLicense(String license) {
		Member member = null;

		String sql="select * from member where license=?";
		
		try {
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setString(1, license);
			ResultSet rs=ps.executeQuery();
			
			if(rs.next())
			{
				member=new Member();
				
				member.setId(rs.getInt("id"));
				member.setAccount(rs.getString("account"));
				member.setPassword(rs.getString("password"));
				member.setName(rs.getString("name"));
				member.setPhone(rs.getString("phone"));
				member.setLicense(rs.getString("license"));
				member.setCategory(rs.getString("category"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return member;
	}
	
	@Override
	public Member readAccount(String account) {
		Member member = null;

		String sql="select * from member where account=?";
		
		try {
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setString(1, account);
			ResultSet rs=ps.executeQuery();
			
			if(rs.next())
			{
				member=new Member();
				
				member.setId(rs.getInt("id"));
				member.setAccount(rs.getString("account"));
				member.setPassword(rs.getString("password"));
				member.setName(rs.getString("name"));
				member.setPhone(rs.getString("phone"));
				member.setLicense(rs.getString("license"));
				member.setCategory(rs.getString("category"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return member;
	}

	@Override
	public Member login(String account, String password) {
		Member member = null;

		String sql="select * from member where account=? and password=?";
		
		try {
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setString(1, account);
			ps.setString(2, password);
			ResultSet rs=ps.executeQuery();
			
			if(rs.next())
			{
				member=new Member();
				member.setId(rs.getInt("id"));
				member.setAccount(rs.getString("account"));
				member.setPassword(rs.getString("password"));
				member.setName(rs.getString("name"));
				member.setPhone(rs.getString("phone"));
				member.setLicense(rs.getString("license"));
				member.setCategory(rs.getString("category"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return member;
	}
	
	@Override
	public List<Member> readAllList() {
		String sql="select * from member";
		
		List<Member> monthList = new ArrayList<Member>();
		
		try
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();

			while(rs.next())
			{
	
				Member member = new Member();
				
				member.setId(rs.getInt("id"));
				member.setAccount(rs.getString("account"));
				member.setPassword(rs.getString("password"));
				member.setName(rs.getString("name"));
				member.setPhone(rs.getString("phone"));
				member.setLicense(rs.getString("license"));
				member.setCategory(rs.getString("category"));
				
				monthList.add(member);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return monthList;
	}
	
	
	@Override
	public List<Member> readLicenseList(String license) {
		String sql="select * from member where license=?";
		
		List<Member> memberList = new ArrayList();
		
		try
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, license);
			ResultSet rs=ps.executeQuery();

			while(rs.next())
			{
				Member member = new Member();
				
				member.setId(rs.getInt("id"));
				member.setAccount(rs.getString("account"));
				member.setPassword(rs.getString("password"));
				member.setName(rs.getString("name"));
				member.setPhone(rs.getString("phone"));
				member.setLicense(rs.getString("license"));
				member.setCategory(rs.getString("category"));
				
				memberList.add(member);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return memberList;
	}
	
	@Override
	public List<Member> readAccountList(String account) {
		String sql="select * from member where account=?";
		
		List<Member> memberList = new ArrayList();
		
		try
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, account);
			ResultSet rs=ps.executeQuery();

			while(rs.next())
			{
				Member member = new Member();
				
				member.setId(rs.getInt("id"));
				member.setAccount(rs.getString("account"));
				member.setPassword(rs.getString("password"));
				member.setName(rs.getString("name"));
				member.setPhone(rs.getString("phone"));
				member.setLicense(rs.getString("license"));
				member.setCategory(rs.getString("category"));
				
				memberList.add(member);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return memberList;
	}
	
	@Override
	public void update(Member member) {
		String sql="update member set password=?,name=?,phone=?,license=?,category=? where id=?";
		
		try {
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setString(1, member.getPassword());
			ps.setString(2, member.getName());
			ps.setString(3, member.getPhone());
			ps.setString(4, member.getLicense());
			ps.setString(5, member.getCategory());
			ps.setInt(6, member.getId());
			
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void delete(Member member) {
		String sql="delete from member where id=?";
		
		try {
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setInt(1, member.getId());
			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}




}
