package hu.diablo.sims4.mod.checker.ui.dir.selector;

import javax.swing.JFileChooser;

import org.apache.log4j.Logger;

import hu.diablo.sims4.mod.checker.ui.MainWindow;

public class DirChooser {
	JFileChooser fileChooser;
	String chosenFolder;
	Boolean isInitDone;
	
	Logger logger = Logger.getLogger(MainWindow.class);
	
	public DirChooser() {
		fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
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
	}
	
	public String getFolderPath() {
		return chosenFolder;
	}
}
