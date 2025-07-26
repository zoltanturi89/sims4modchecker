package hu.diablo.sims4.mod.checker.utils;

import java.util.Date;

import hu.diablo.sims4.mod.checker.dbpf.exception.DBPFReadException;

public class ByteConverterUtils {

	public static String parseDBPFIDentifier(byte[] headerData) throws DBPFReadException {
		String dbpfSpecStr = "";
		for(int i = 0; i < headerData.length; ++i) {
			dbpfSpecStr += (char)headerData[i];
		}
		
		if(!dbpfSpecStr.equals("DBPF")) {
			throw new DBPFReadException("DBPF file header was invalid!");
		}
		
		return dbpfSpecStr;
	}
	
	public static String parseVersionData(byte[] headerData) {
		String versionData = "";
		for(int i = 0; i < headerData.length; ++i) {
			versionData += Byte.toUnsignedInt(headerData[i]);
		}
		
		return versionData;
	}
	
	public static Long parseLongValue(byte[] headerData) {
		Long base = 256L;
		Long value = 0L;
		for(int i = headerData.length-1; i >= 0; --i) {
			value = (value * base) + Byte.toUnsignedLong(headerData[i]);
		}
		return value;
	}
	
	public static Date parseDateValue(byte[] headerData) {
		Long unixTimestamp = parseLongValue(headerData);
		return new Date(unixTimestamp*1000);
	}
}
