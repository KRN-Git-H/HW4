package service;

import java.util.List;

import model.MemberLicense;

public interface MemberLicenseService {
	//create
	boolean create(MemberLicense memberLicense);
	
	//read
	MemberLicense readLicense(String license);
	List<MemberLicense> readAll();
	List<MemberLicense>	readAccountList(String account);
	List<MemberLicense> readLicenseList(String license);	
	
	//update
	boolean update(MemberLicense memberLicense);
	
	//delete
	boolean delete(MemberLicense memberLicense);
}
