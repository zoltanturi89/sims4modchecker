package hu.diablo.sims4.mod.checker.ui.dir.selector;

import javax.swing.JFileChooser;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hu.diablo.sims4.mod.checker.backend.parser.Sims4ModDataParser;
import hu.diablo.sims4.mod.checker.ui.MainWindow;

@Component
public class DirChooser {
	JFileChooser fileChooser;
	String chosenFolder;
	Boolean isInitDone;
	
	Logger logger = Logger.getLogger(MainWindow.class);
	
	Sims4ModDataParser dataParser;
	
	@Autowired
	public DirChooser(Sims4ModDataParser dataParser) {
		fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		this.dataParser = dataParser;

	}
	
	public void showDialog() {
		int returnVal = fileChooser.showOpenDialog(null);
		if(returnVal == JFileChooser.ERROR_OPTION) {
			throw new RuntimeException("Error while chosing mod folder!");
		} else if(returnVal == JFileChooser.CANCEL_OPTION) {
			chosenFolder = null;
		} else if(returnVal == JFileChooser.APPROVE_OPTION) {
			chosenFolder = fileChooser.getSelectedFile().getAbsolutePath();
			logger.info("Directory choosen:" + chosenFolder);
		}
		
		isInitDone = true;
		dataParser.setModDir(chosenFolder);
	}
	
	public String getFolderPath() {
		return chosenFolder;
	}
}
