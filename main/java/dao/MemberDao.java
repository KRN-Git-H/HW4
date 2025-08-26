package dao;

import java.util.List;

import model.Member;
import model.MonthRent;

public interface MemberDao {
	//create
	void create(Member member);
	
	//read
	Member readLicense(String license);
	Member readAccount(String account);
	Member login(String account,String password);
	List<Member> readAllList();
	List<Member> readLicenseList(String license);	
	List<Member> readAccountList(String license);	
	
	//update
	void update(Member member);
	
	//delete
	void delete(Member member);
}
