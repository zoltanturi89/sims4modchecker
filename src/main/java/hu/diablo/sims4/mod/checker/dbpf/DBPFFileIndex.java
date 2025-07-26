package hu.diablo.sims4.mod.checker.dbpf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hu.diablo.sims4.mod.checker.dbpf.index.DBPFBaseIndexEntry;
import lombok.Getter;

@Getter
public class DBPFFileIndex {
	List<DBPFBaseIndexEntry> indexEntries;
	int version;
	int entryCount;
	
	public static final int IDX_VERSION_1_SIZE = 24;
	public static final int IDX_VERSION_2_SIZE = 20;
	public static final int IDX_VERSION_3_SIZE = 24;
	
	public DBPFFileIndex(int version,int entryCount) {
		indexEntries = new ArrayList<DBPFBaseIndexEntry>();
		this.version = version;

		this.entryCount = entryCount;
	}
	
	public void parseIndexData(byte[] header) {
		int startIdx = 0;
		int entrySize = 
				DBPFBaseIndexEntry.createIndexEntry(version).getEntrySize();
		for(int i = 0; i < entryCount; ++i) {
			byte[] entryDetails = Arrays.copyOfRange(header,startIdx, startIdx + entrySize);
			
			DBPFBaseIndexEntry indexEntry = 
					DBPFBaseIndexEntry.createIndexEntry(version);
			
			indexEntry.parseData(entryDetails);
			
			this.indexEntries.add(indexEntry);
			startIdx = startIdx + entrySize;
		}
	}
}
