package mod.checker.backend.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import hu.diablo.sims4.mod.checker.model.ModType;
import hu.diablo.sims4.mod.checker.model.SimsModDetails;
import hu.diablo.sims4.mod.checker.model.cache.ModDetailsCache;
import hu.diablo.sims4.mod.checker.model.list.SimsModList;

public class Sims4ModDataParser {
	
	private String modDir;
	private static Logger log = Logger.getLogger(Sims4ModDataParser.class);
	
	private ModDetailsCache cache = ModDetailsCache.getInstance();
	
	private SimsModList simsModList;
	
	public Sims4ModDataParser(String modDir) {
		this.modDir = modDir;
		this.simsModList = new SimsModList();
	}
	
	public String getModDir() {
		return modDir;
	}
	
	public SimsModList getModList() {
		return this.simsModList;
	}
	
	public void parseMods() {
		log.info("Starting mod data parsing!");
		
		File baseFolder = new File(modDir);
		
		Collection<File> modFiles = FileUtils.listFiles(baseFolder, new String[] {"ts4script","package"}, true);
		
		modFiles.stream().forEach(item -> {
			if(item.isDirectory()) {
				return;
			}
			
			SimsModDetails details = new SimsModDetails();
			details.setModName(FilenameUtils.getBaseName(item.getName()));
			
			ArrayList<String> modFilesList = new ArrayList<String>();
			modFilesList.add(item.getName());
			
			details.setModFiles(modFilesList);
			
			switch(FilenameUtils.getExtension(item.getName())) {
			case "package":
				details.setModType(ModType.SKIN_MOD);
				break;
			case "ts4script":
				details.setModType(ModType.SCRIPT_MOD);
				break;
			default:
				details.setModType(ModType.UNKNOWN);
				break;
			}
			
			cache.addDetails(details);
			simsModList.addModToModList(details);
			
			log.trace("Adding " + details.getModName() + " to cache!");
		});
		
		log.info("Mod data parsing finished!");
	}
}
