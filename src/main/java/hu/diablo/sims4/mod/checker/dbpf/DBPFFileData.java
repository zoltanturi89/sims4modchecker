package hu.diablo.sims4.mod.checker.dbpf;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DBPFFileData {
	String typeId;
	String groupId;
	String instanceId;
	String instanceId2;
	Long fileLoc;
	Long fileSize;
	
	public void parseFileData(byte[] indexData) {
		
	}
}
