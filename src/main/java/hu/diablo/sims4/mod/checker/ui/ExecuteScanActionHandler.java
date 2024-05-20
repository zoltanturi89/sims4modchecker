package hu.diablo.sims4.mod.checker.ui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hu.diablo.sims4.mod.checker.backend.parser.Sims4ModDataParser;
import hu.diablo.sims4.mod.checker.entities.repositories.SimsModDetailsRepository;

@Component
public class ExecuteScanActionHandler implements MouseListener {

	Logger logger = Logger.getLogger(ExecuteScanActionHandler.class);
	
	File baseFolder;
	
	SimsModDetailsRepository detailsRepository;
	
	Sims4ModDataParser dataParser;
	
	MainWindow baseWindow;
	
	@Autowired
	public ExecuteScanActionHandler(SimsModDetailsRepository repository,
			Sims4ModDataParser dataParser) {
		this.detailsRepository = repository;
		this.dataParser = dataParser;
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
		
		dataParser.parseMods();
		
		baseWindow.reDrawTable();
	}
	
	public void setBaseDir(String dir) {
		this.dataParser.setModDir(dir);
	}
	
	public void setBaseWindow(MainWindow window) {
		this.baseWindow = window;
	}
}
