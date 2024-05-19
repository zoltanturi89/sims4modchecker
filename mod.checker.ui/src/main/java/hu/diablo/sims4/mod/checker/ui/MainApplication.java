package hu.diablo.sims4.mod.checker.ui;

import org.apache.log4j.BasicConfigurator;
public class MainApplication {
	public static void main(String[] args) {
		BasicConfigurator.configure();
		new MainWindow();
	}
}
