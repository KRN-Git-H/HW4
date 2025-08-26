package dao;

import java.util.List;
import model.ParkingRecord;

public interface ParkingRecordDao {
	//create
	void create(ParkingRecord parkingrecord);
	
	//read
	List<ParkingRecord> readAllList();	
	List<ParkingRecord> readLicenseList(String license);

	//update
	
	//delete

}
