package hu.diablo.sims4.mod.checker.backend.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.StreamSupport;

import javax.transaction.Transactional;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hu.diablo.sims4.mod.checker.dbpf.DBPFArchive;
import hu.diablo.sims4.mod.checker.enitites.ModFile;
import hu.diablo.sims4.mod.checker.enitites.ModType;
import hu.diablo.sims4.mod.checker.enitites.SimsModDetails;
import hu.diablo.sims4.mod.checker.entities.repositories.SimsModDetailsRepository;
import hu.diablo.sims4.mod.checker.script.ScriptModPackage;

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
	
	@Transactional
	public void parseMods() {
		log.info("Starting mod data parsing!");
		
		File baseFolder = new File(modDir);
		
		Collection<File> modFiles = FileUtils.listFiles(baseFolder, new String[] {"ts4script","package"}, true);
		
		modFiles.stream().forEach(item -> {
			if(item.isDirectory()) {
				return;
			}
			
			String modName = FilenameUtils.getBaseName(item.getName());
			
			ModFile modFile = new ModFile(0, FilenameUtils.getName(item.getName()),
					FilenameUtils.getExtension(item.getName()), item.getAbsolutePath(), null);
			
			Optional<SimsModDetails> data = findDifferentVersions(modName);
			
			SimsModDetails details = null;
			if(data.isPresent()) {
				details = data.get();
				modFile.setModDetails(details);
				details.getModFiles().add(modFile);
			} else {
				details = new SimsModDetails();
				details.setModName(FilenameUtils.getBaseName(item.getName()));
				
				ArrayList<ModFile> modFilesList = new ArrayList<ModFile>();
				
				modFilesList.add(modFile);
				
				details.setModFiles(modFilesList);
				
				int c = 0;
				switch(FilenameUtils.getExtension(item.getName())) {
				case "package":
					if(c == 0) {
						DBPFArchive packageFile = new DBPFArchive();
						try {
							packageFile.readDBPFFile(item.getAbsolutePath());
						} catch(Exception ex) {
							log.error(ex.getMessage(),ex);
						}
						++c;
					}
					
					details.setModType(ModType.SKIN_MOD);
					break;
				case "ts4script":
					details.setModType(ModType.SCRIPT_MOD);
					ScriptModPackage scriptModPackage = new ScriptModPackage(item.getAbsolutePath());
					break;
				default:
					details.setModType(ModType.UNKNOWN);
					break;
				}
			}
			
			modDetailsRepository.save(details);
			
			log.trace("Adding " + details.getModName() + " to cache!");
		});
		
		log.info("Mod data parsing finished!");
	}
	
	private Optional<SimsModDetails> findDifferentVersions(String modName){
		Iterable<SimsModDetails> sameModNames = modDetailsRepository.findByModName(modName);
		
		
		return StreamSupport.stream(sameModNames.spliterator(), false).findFirst();
	}
}
