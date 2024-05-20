package hu.diablo.sims4.mod.checker.dbpf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Getter;

@Getter
public class DBPFFileIndex {
	List<DBPFFileData> fileData;
	int version;
	int entrySize;
	int entryCount;
	
	public static final int IDX_VERSION_1_SIZE = 24;
	public static final int IDX_VERSION_2_SIZE = 20;
	public static final int IDX_VERSION_3_SIZE = 24;
	
	public DBPFFileIndex(int version,int entryCount) {
		fileData = new ArrayList<DBPFFileData>();
		this.version = version;
		switch(version) {
		case 1:
			this.entrySize = IDX_VERSION_1_SIZE;
			break;
		case 2:
			this.entrySize = IDX_VERSION_2_SIZE;
			break;
		case 3:
			this.entrySize = IDX_VERSION_3_SIZE;
			break;
		}
		
		this.entryCount = entryCount;
	}
	
	public void parseIndexData(byte[] header) {
		int startIdx = 0;
		for(int i = 0; i < entryCount; ++i) {
			DBPFFileData newFileData = new DBPFFileData();
			
			byte[] entryDetails = Arrays.copyOfRange(header,startIdx, startIdx + entrySize);
			
			newFileData.parseFileData(entryDetails);
			
			this.fileData.add(newFileData);
		}
	}
}
