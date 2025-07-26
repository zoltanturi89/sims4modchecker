package hu.diablo.sims4.mod.checker.dbpf.index;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class DBPFBaseIndexEntry {
	private int version;
	
	protected long location;
	protected long fileSize;
	
	public static final int DBPF_IDX_FIELD_LEN = 4;
	
	abstract public int getEntrySize();
	
	abstract public void parseData(byte[] header);
	
	public static DBPFBaseIndexEntry createIndexEntry(int version) {
		DBPFBaseIndexEntry retVal = null;
		if(version == 1000 || version == 3000) {
			retVal = new DBPF71IndexEntry();
		} else if(version == 2000) {
			retVal = new DBPF70IndexEntry();
		}
		
		return retVal;
	}
}
