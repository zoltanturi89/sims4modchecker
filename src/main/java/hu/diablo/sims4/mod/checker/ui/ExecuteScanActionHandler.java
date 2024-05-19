package hu.diablo.sims4.mod.checker.ui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import hu.diablo.sims4.mod.checker.cache.ModDetailsCache;
import hu.diablo.sims4.mod.checker.models.ModType;
import hu.diablo.sims4.mod.checker.models.SimsModDetails;

public class ExecuteScanActionHandler implements MouseListener {

	Logger logger = Logger.getLogger(ExecuteScanActionHandler.class);
	
	String baseDir;
	SimsModDetails modDetails;
	
	File baseFolder;
	
	ModDetailsCache cache;
	
	public ExecuteScanActionHandler(String dir) {
		this.baseDir = dir;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		logger.info("Executing Scan on mod folder!");
		
		baseFolder = new File(baseDir);
		
		Collection<File> modFiles = FileUtils.listFiles(baseFolder, new String[] {"ts4script","package"}, true);
		
		cache = ModDetailsCache.getInstance();
		
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
			
			logger.trace("Adding " + details.getModName() + " to cache!");
		});
	}
	
	public void setBaseDir(String baseDir) {
		this.baseDir = baseDir;
	}

}
