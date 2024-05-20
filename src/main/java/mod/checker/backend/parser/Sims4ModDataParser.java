package mod.checker.backend.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hu.diablo.sims4.mod.checker.enitites.ModType;
import hu.diablo.sims4.mod.checker.enitites.SimsModDetails;
import hu.diablo.sims4.mod.checker.entities.repositories.SimsModDetailsRepository;

@Component
public class Sims4ModDataParser {
	
	private String modDir;
	private static Logger log = Logger.getLogger(Sims4ModDataParser.class);
	
	private SimsModDetailsRepository modDetailsRepository;
	
	@Autowired
	public Sims4ModDataParser(SimsModDetailsRepository repository) {
		this.modDetailsRepository = repository;
	}
	
	public void setModDir(String dir) {
		this.modDir = dir;
	}
	
	public String getModDir() {
		return modDir;
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
			
			
			
			modDetailsRepository.save(details);
			
			log.trace("Adding " + details.getModName() + " to cache!");
		});
		
		log.info("Mod data parsing finished!");
	}
}
