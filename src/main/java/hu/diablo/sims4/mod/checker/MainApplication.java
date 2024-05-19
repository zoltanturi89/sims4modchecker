package hu.diablo.sims4.mod.checker;

import org.apache.log4j.BasicConfigurator;

import hu.diablo.sims4.mod.checker.ui.MainWindow;

public class MainApplication {
	public static void main(String[] args) {
		BasicConfigurator.configure();
		MainWindow mainwindow = new MainWindow();
		
		mainwindow.show();
	}
}
