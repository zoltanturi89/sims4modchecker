package hu.diablo.sims4.mod.checker.dbpf;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Date;

import hu.diablo.sims4.mod.checker.dbpf.exception.DBPFReadException;
import hu.diablo.sims4.mod.checker.utils.ByteConverterUtils;
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
		
		this.setDbpfIdentifier(ByteConverterUtils.parseDBPFIDentifier(headerData));
		
		//Versions
		headerData = Arrays.copyOfRange(header, startIdx,startIdx + len);
		this.setMajorVersion(ByteConverterUtils.parseVersionData(headerData));
		startIdx += len;
		
		headerData = Arrays.copyOfRange(header, startIdx,startIdx + len);
		this.setMinorVersion(ByteConverterUtils.parseVersionData(headerData));
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
		this.setDateCreated(ByteConverterUtils.parseDateValue(headerData));
		startIdx += len;
		
		headerData = Arrays.copyOfRange(header, startIdx,startIdx + len);
		this.setDateModified(ByteConverterUtils.parseDateValue(headerData));
		startIdx += len;
		
		//Index details
		headerData = Arrays.copyOfRange(header, startIdx,startIdx + len);
		this.setIndexMajorVersion(ByteConverterUtils.parseVersionData(headerData));
		startIdx += len;
		
		headerData = Arrays.copyOfRange(header, startIdx,startIdx + len);
		this.setEntryCount(ByteConverterUtils.parseLongValue(headerData));
		startIdx += len;
		
		headerData = Arrays.copyOfRange(header, startIdx,startIdx + len);
		this.setFirstIndexOffset(ByteConverterUtils.parseLongValue(headerData));
		startIdx += len;
		
		headerData = Arrays.copyOfRange(header, startIdx,startIdx + len);
		this.setIndexSize(ByteConverterUtils.parseLongValue(headerData));
		startIdx += len;
		
		//Hole Entry details
		headerData = Arrays.copyOfRange(header, startIdx,startIdx + len);
		this.setHoleEntryCount(ByteConverterUtils.parseLongValue(headerData));
		startIdx += len;
		
		headerData = Arrays.copyOfRange(header, startIdx,startIdx + len);
		this.setHoleOffset(ByteConverterUtils.parseLongValue(headerData));
		startIdx += len;
		
		headerData = Arrays.copyOfRange(header, startIdx,startIdx + len);
		this.setHoleSize(ByteConverterUtils.parseLongValue(headerData));
		startIdx += len;
		
		headerData = Arrays.copyOfRange(header, startIdx,startIdx + len);
		this.setIndexMinorVersion(ByteConverterUtils.parseVersionData(headerData));
		startIdx += len;
		
		headerData = Arrays.copyOfRange(header, startIdx,startIdx + len);
		this.setIndexOffset(ByteConverterUtils.parseLongValue(headerData));
		startIdx += len;
		
		headerData = Arrays.copyOfRange(header, startIdx,startIdx + len);
		this.setUnknown4(new String(headerData,DBPF_CHARSET));
		startIdx += len;
		
		headerData = Arrays.copyOfRange(header, startIdx,startIdx + len);
		this.setReserved(new String(headerData,DBPF_CHARSET));
		startIdx += len;
		
	}
	

}
