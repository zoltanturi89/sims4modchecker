package hu.diablo.sims4.mod.checker.ui.dir.selector;

import java.awt.event.MouseListener;
import org.apache.log4j.Logger;

import hu.diablo.sims4.mod.checker.ui.MainWindow;

public class MouseClickActionHandler implements MouseListener {
	DirChooser fileChooser;
	static Logger logger = Logger.getLogger(MouseClickActionHandler.class);
	
	MainWindow baseComp;
	
	public MouseClickActionHandler(DirChooser fileChooser, MainWindow baseComp) {
		this.fileChooser = fileChooser;
		this.baseComp = baseComp; 
	}

	@Override
	public void mouseClicked(java.awt.event.MouseEvent e) {
	}

	@Override
	public void mouseEntered(java.awt.event.MouseEvent e) {
	}

	@Override
	public void mouseExited(java.awt.event.MouseEvent e) {
	}

	@Override
	public void mousePressed(java.awt.event.MouseEvent e) {
	}

	@Override
	public void mouseReleased(java.awt.event.MouseEvent e) {	
		fileChooser.showDialog();
		
		baseComp.updateModDir();
	}

}
