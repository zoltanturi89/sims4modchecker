package hu.diablo.sims4.mod.checker.ui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import hu.diablo.sims4.mod.checker.model.ModType;
import hu.diablo.sims4.mod.checker.model.SimsModDetails;
import hu.diablo.sims4.mod.checker.model.cache.ModDetailsCache;
import mod.checker.backend.checks.CheckVersions;
import mod.checker.backend.parser.Sims4ModDataParser;


public class ExecuteScanActionHandler implements MouseListener {

	Logger logger = Logger.getLogger(ExecuteScanActionHandler.class);
	
	String baseDir;
	
	File baseFolder;
	
	ModDetailsCache cache;
	
	Sims4ModDataParser dataParser;
	
	CheckVersions versionChecker;
	
	public ExecuteScanActionHandler(String dir) {
		this.baseDir = dir;
		this.dataParser = new Sims4ModDataParser(dir);
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
		
		dataParser.parseMods();
		
		versionChecker = new CheckVersions(dataParser.getModList());
		
		versionChecker.checkVersions();
	}
	
	public void setBaseDir(String baseDir) {
		this.baseDir = baseDir;
	}

}
