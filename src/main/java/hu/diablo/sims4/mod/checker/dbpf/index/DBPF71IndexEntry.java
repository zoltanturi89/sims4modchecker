package hu.diablo.sims4.mod.checker.dbpf.index;

import java.util.Arrays;

import hu.diablo.sims4.mod.checker.utils.ByteConverterUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DBPF71IndexEntry extends DBPFBaseIndexEntry {
	private String typeId;
	private String groupId;
	private String instanceId;
	private String instanceId2;
	
	@Override
	public int getEntrySize() {
		return 24;
	}
	
	@Override
	public void parseData(byte[] header) {
		int startIdx = 0;
		int len = DBPF_IDX_FIELD_LEN;
		byte[] headerData = Arrays.copyOfRange(header, startIdx, startIdx + len);
		startIdx += len;
		this.setTypeId(ByteConverterUtils.parseVersionData(headerData));
		
		headerData = Arrays.copyOfRange(header, startIdx, startIdx + len);
		startIdx += len;
		this.setGroupId(ByteConverterUtils.parseVersionData(headerData));
		
		headerData = Arrays.copyOfRange(header, startIdx, startIdx + len);
		startIdx += len;
		this.setInstanceId(ByteConverterUtils.parseVersionData(headerData));
		
		headerData = Arrays.copyOfRange(header, startIdx, startIdx + len);
		startIdx += len;
		this.setInstanceId2(ByteConverterUtils.parseVersionData(headerData));
		
		headerData = Arrays.copyOfRange(header, startIdx, startIdx + len);
		startIdx += len;
		this.setLocation(ByteConverterUtils.parseLongValue(headerData));
		
		headerData = Arrays.copyOfRange(header, startIdx, startIdx + len);
		startIdx += len;
		this.setFileSize(ByteConverterUtils.parseLongValue(headerData));
	}
}
