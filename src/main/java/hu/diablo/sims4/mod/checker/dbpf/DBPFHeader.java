package hu.diablo.sims4.mod.checker.dbpf;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Date;

import hu.diablo.sims4.mod.checker.dbpf.exception.DBPFReadException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DBPFHeader {
	public static final int DBPF_HEADER_FIELD_LEN = 4;
	public static final Charset DBPF_CHARSET = Charset.forName("UTF-8");
	
	String dbpfIdentifier;
	String majorVersion;
	String minorVersion;
	String unkonwn1;
	String unknown2;
	String unknown3;
	Date DateCreated;
	Date DateModified;
	String indexMajorVersion;
	Long EntryCount;
	Long FirstIndexOffset;
	Long indexSize;
	Long holeEntryCount;
	Long holeOffset;
	Long holeSize;
	String indexMinorVersion;
	Long indexOffset;
	String unknown4;
	String reserved;
	
	public void parseOriginalHeader(byte[] header) throws DBPFReadException {
		int startIdx = 0;
		int len = DBPF_HEADER_FIELD_LEN;
		byte[] headerData = Arrays.copyOfRange(header, startIdx, startIdx + len);
		startIdx += len;
		
		parseDBPFIDentifier(headerData);
		
		//Versions
		headerData = Arrays.copyOfRange(header, startIdx,startIdx + len);
		this.setMajorVersion(parseVersionData(headerData));
		startIdx += len;
		
		headerData = Arrays.copyOfRange(header, startIdx,startIdx + len);
		this.setMinorVersion(parseVersionData(headerData));
		startIdx += len;
		
		//3 unknown fields
		headerData = Arrays.copyOfRange(header, startIdx,startIdx + len);
		this.setUnkonwn1(new String(headerData,DBPF_CHARSET));
		startIdx += len;
		
		headerData = Arrays.copyOfRange(header, startIdx,startIdx + len);
		this.setUnknown2(new String(headerData,DBPF_CHARSET));
		startIdx += len;
		
		headerData = Arrays.copyOfRange(header, startIdx,startIdx + len);
		this.setUnknown3(new String(headerData,DBPF_CHARSET));
		startIdx += len;
		
		//Date Created and Modified
		headerData = Arrays.copyOfRange(header, startIdx,startIdx + len);
		this.setDateCreated(parseDateValue(headerData));
		startIdx += len;
		
		headerData = Arrays.copyOfRange(header, startIdx,startIdx + len);
		this.setDateModified(parseDateValue(headerData));
		startIdx += len;
		
		//Index details
		headerData = Arrays.copyOfRange(header, startIdx,startIdx + len);
		this.setIndexMajorVersion(parseVersionData(headerData));
		startIdx += len;
		
		headerData = Arrays.copyOfRange(header, startIdx,startIdx + len);
		this.setEntryCount(parseLongValue(headerData));
		startIdx += len;
		
		headerData = Arrays.copyOfRange(header, startIdx,startIdx + len);
		this.setFirstIndexOffset(parseLongValue(headerData));
		startIdx += len;
		
		headerData = Arrays.copyOfRange(header, startIdx,startIdx + len);
		this.setIndexSize(parseLongValue(headerData));
		startIdx += len;
		
		//Hole Entry details
		headerData = Arrays.copyOfRange(header, startIdx,startIdx + len);
		this.setHoleEntryCount(parseLongValue(headerData));
		startIdx += len;
		
		headerData = Arrays.copyOfRange(header, startIdx,startIdx + len);
		this.setHoleOffset(parseLongValue(headerData));
		startIdx += len;
		
		headerData = Arrays.copyOfRange(header, startIdx,startIdx + len);
		this.setHoleSize(parseLongValue(headerData));
		startIdx += len;
		
		headerData = Arrays.copyOfRange(header, startIdx,startIdx + len);
		this.setIndexMinorVersion(parseVersionData(headerData));
		startIdx += len;
		
		headerData = Arrays.copyOfRange(header, startIdx,startIdx + len);
		this.setIndexOffset(parseLongValue(headerData));
		startIdx += len;
		
		headerData = Arrays.copyOfRange(header, startIdx,startIdx + len);
		this.setUnknown4(new String(headerData,DBPF_CHARSET));
		startIdx += len;
		
		headerData = Arrays.copyOfRange(header, startIdx,startIdx + len);
		this.setReserved(new String(headerData,DBPF_CHARSET));
		startIdx += len;
		
	}
	
	private void parseDBPFIDentifier(byte[] headerData) throws DBPFReadException {
		String dbpfSpecStr = "";
		for(int i = 0; i < headerData.length; ++i) {
			dbpfSpecStr += (char)headerData[i];
		}
		
		if(!dbpfSpecStr.equals("DBPF")) {
			throw new DBPFReadException("DBPF file header was invalid!");
		}
		
		this.setDbpfIdentifier(dbpfSpecStr);
	}
	
	private String parseVersionData(byte[] headerData) {
		String versionData = "";
		for(int i = 0; i < headerData.length; ++i) {
			versionData += Byte.toUnsignedInt(headerData[i]);
		}
		
		return versionData;
	}
	
	private Long parseLongValue(byte[] headerData) {
		Long base = 256L;
		Long value = 0L;
		for(int i = headerData.length-1; i >= 0; --i) {
			value = (value * base) + Byte.toUnsignedLong(headerData[i]);
		}
		return value;
	}
	
	private Date parseDateValue(byte[] headerData) {
		Long unixTimestamp = parseLongValue(headerData);
		return new Date(unixTimestamp*1000);
	}
}
