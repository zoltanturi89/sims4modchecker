package hu.diablo.sims4.mod.checker.dbpf;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import org.apache.log4j.Logger;

import hu.diablo.sims4.mod.checker.dbpf.exception.DBPFReadException;

public class DBPFFile {
	private File srcFile;
	
	private DBPFHeader fileHeader;
	
	private byte[] baseHeader;
	
	private static final Logger log = Logger.getLogger(DBPFFile.class);
	
	public DBPFFile() {
	}
	
	public void readDBPFFile(String path) throws IOException, DBPFReadException {
		this.srcFile = new File(path);
		
		FileInputStream fis = new FileInputStream(srcFile);
		
		byte[] header = new byte[96];
		int byteCount = fis.read(header);
		
		if(byteCount != 96) {
			throw new DBPFReadException("DBPF file header was invalid!");
		}
		
		baseHeader = header;
		
		fileHeader = new DBPFHeader();
		
		fileHeader.parseOriginalHeader(baseHeader);
		byte[] skipOffset = new byte[fileHeader.getIndexOffset().intValue()];
		
		fis.read(skipOffset);
		
		byte[] fileIndex = new byte[fileHeader.indexSize.intValue()];
		
		fis.read(fileIndex);
	
		
		fis.close(); // clean up;
	}
}
