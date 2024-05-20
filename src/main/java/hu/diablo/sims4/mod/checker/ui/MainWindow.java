package hu.diablo.sims4.mod.checker.ui;

import java.awt.Dimension;
import java.util.List;
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
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hu.diablo.sims4.mod.checker.enitites.SimsModDetails;
import hu.diablo.sims4.mod.checker.entities.repositories.SimsModDetailsRepository;
import hu.diablo.sims4.mod.checker.ui.dir.selector.DirChooser;
import hu.diablo.sims4.mod.checker.ui.dir.selector.MouseClickActionHandler;
import hu.diablo.sims4.mod.checker.ui.table.ConflictRowHighligther;

@Component
public class MainWindow extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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
	
	SimsModDetailsRepository modDetailsRepository;
	ConflictRowHighligther conflictRowHighligther;
	
	@Autowired
	public MainWindow(DirChooser sims4ModDirChooser, 
			ExecuteScanActionHandler executeScanActionHandler,
			SimsModDetailsRepository modDetailsRepository,
			ConflictRowHighligther conflictRowHighligther) {
		this.sims4ModDirChooser = sims4ModDirChooser;
		this.executeScanActionHandler = executeScanActionHandler;
		this.modDetailsRepository = modDetailsRepository;
		this.conflictRowHighligther = conflictRowHighligther;
		
		executeScanActionHandler.setBaseWindow(this);
		
		this.setSize(800, 800);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
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
		
		this.add(panel);
		
		this.setJMenuBar(headerBar);
		
		this.addWindowListener(new WindowActionHandler(this));
		
		tablePanel = new JScrollPane();
		tablePanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		tablePanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		tableModel = new DefaultTableModel(null, 
				new Object[] {"ID","Mod name","Mod type","Mod files"});
		
		baseTable = new JTable(tableModel);
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(baseTable.getModel());
		baseTable.getColumnModel().getColumn(0).setCellRenderer(conflictRowHighligther);
		baseTable.getColumnModel().getColumn(1).setCellRenderer(conflictRowHighligther);
		baseTable.getColumnModel().getColumn(2).setCellRenderer(conflictRowHighligther);
		baseTable.getColumnModel().getColumn(3).setCellRenderer(conflictRowHighligther);
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
		
		sims4ModDirChooser.showDialog();
		
		logger.info("Selected directory:" + sims4ModDirChooser.getFolderPath());
		
		executeScan.addMouseListener(executeScanActionHandler);
		
		this.pack();
		this.setLocationByPlatform(true);
	}

	public void updateModDir() {
		executeScanActionHandler.setBaseDir(sims4ModDirChooser.getFolderPath());	
	}
	
	@Transactional
	public void reDrawTable() {
		tableModel.setRowCount(0);
		
		List<SimsModDetails> modDetails = modDetailsRepository.findAll();
		
		modDetails.stream().forEach(details -> {
			Vector<Object> addRowV = new Vector<Object>();
			addRowV.add(details.getId());
			addRowV.add(details.getModName());
			addRowV.add(details.getModType());
			addRowV.add(details.getModFilesAsString());
			
			tableModel.addRow(addRowV);
		});

		tableModel.fireTableRowsInserted(0, tableModel.getRowCount()-1);
	}
}

