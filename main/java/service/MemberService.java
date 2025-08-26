package service;

import java.util.List;

import model.Member;
import model.MonthRent;

public interface MemberService {
	//create
	boolean create(Member member);

	//read
	int readAmount(String license);
	Member readAccount(String account);	
	Member login(String username,String password);
	List<Member> readAll();
	boolean readLicenseList(String license);
	
	//update
	boolean update(Member member);
	
	//delete
	boolean delete(Member member);
}

