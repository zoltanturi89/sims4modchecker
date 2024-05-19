package hu.diablo.sims4.mod.checker.ui;

import java.awt.Dimension;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.apache.log4j.Logger;

import hu.diablo.sims4.mod.checker.cache.IChangeEventListener;
import hu.diablo.sims4.mod.checker.cache.ModDetailsCache;
import hu.diablo.sims4.mod.checker.models.SimsModDetails;
import hu.diablo.sims4.mod.checker.ui.dir.selector.DirChooser;
import hu.diablo.sims4.mod.checker.ui.dir.selector.MouseClickActionHandler;

public class MainWindow implements IChangeEventListener {
	JFrame mainWindow;
	JMenuBar headerBar;
	JMenu fileMenu;
	JMenu modMenu;
	JMenuItem reselectModDir;
	
	JTable baseTable;
	DefaultTableModel tableModel;
	
	JButton executeScan;
	
	JScrollPane tablePanel;
	
	DirChooser sims4ModDirChooser;
	
	ExecuteScanActionHandler executeScanActionHandler;
	
	Logger logger = Logger.getLogger(MainWindow.class);
	
	public MainWindow() {
		mainWindow = new JFrame("Sims 4 Mod checker");
		mainWindow.setSize(800, 800);
		mainWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		sims4ModDirChooser = new DirChooser();
		
		fileMenu = new JMenu("File");
		
		modMenu = new JMenu("Mod Settings");
		
		reselectModDir = new JMenuItem("Choose Mod Directory...");
		reselectModDir.addMouseListener(new MouseClickActionHandler(sims4ModDirChooser,this));
		
		modMenu.add(reselectModDir);
		
		headerBar = new JMenuBar();
		headerBar.setSize(800, 20);
		headerBar.setPreferredSize(new Dimension(800, 20));
		headerBar.add(fileMenu);
		headerBar.add(modMenu);
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(800,780));
		panel.setMaximumSize(panel.getPreferredSize());
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		mainWindow.add(panel);
		
		mainWindow.setJMenuBar(headerBar);
		
		mainWindow.addWindowListener(new WindowActionHandler(this.mainWindow));
		
		tablePanel = new JScrollPane();
		tablePanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		tablePanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		tableModel = new DefaultTableModel(null, 
				new Object[] {"Mod name","Mod type","Mod files"});
		
		baseTable = new JTable(tableModel);
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(baseTable.getModel());
		baseTable.setRowSorter(sorter);
		baseTable.setFillsViewportHeight(true);
		baseTable.setPreferredScrollableViewportSize(new Dimension(800,800));
		
		tablePanel.getViewport().add(baseTable);
		panel.add(tablePanel);
		tablePanel.repaint();
		
		executeScan = new JButton("Execute Scan!");
		executeScan.setSize(40, 20);
		executeScan.setPreferredSize(new Dimension(40, 20));
		
		panel.add(executeScan);
		
		ModDetailsCache.getInstance().addChangeListener(this);
	}
	
	public void show() {
		sims4ModDirChooser.showDialog();
		
		logger.info("Selected directory:" + sims4ModDirChooser.getFolderPath());
		
		executeScanActionHandler = new ExecuteScanActionHandler(sims4ModDirChooser.getFolderPath());
		executeScan.addMouseListener(executeScanActionHandler);
		
		mainWindow.pack();
		mainWindow.setLocationByPlatform(true);
		mainWindow.setVisible(true);	
	}
	
	public void updateModDir() {
		executeScanActionHandler.setBaseDir(sims4ModDirChooser.getFolderPath());		
	}

	@Override
	public void objectAdded(Object added) {
		SimsModDetails details = (SimsModDetails)added;
		Vector<Object> addRowV = new Vector<Object>();
		addRowV.add(details.getModName());
		addRowV.add(details.getModType());
		addRowV.add(details.getModFilesAsString());
		
		tableModel.addRow(addRowV);
		tableModel.fireTableRowsInserted(tableModel.getRowCount()-1, tableModel.getRowCount()-1);
	}

	@Override
	public void objectModified(Object modified) {
		SimsModDetails details = (SimsModDetails)modified;
		
		tableModel.setValueAt(details.getModFilesAsString(), details.getId()+1, 2);
		tableModel.fireTableRowsUpdated(details.getId()+1, details.getId()+1);
	}
}

