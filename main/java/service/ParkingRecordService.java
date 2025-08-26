package service;

import java.util.List;
import model.ParkingRecord;

public interface ParkingRecordService {
	//create
	boolean create(ParkingRecordService monthrent);

	//read
	List<ParkingRecord> readAll();
	List<ParkingRecord> readLicenseList(String license);
	
	//update
	
	//delete
}
