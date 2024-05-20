package hu.diablo.sims4.mod.checker.ui.table;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import hu.diablo.sims4.mod.checker.enitites.SimsModDetails;
import hu.diablo.sims4.mod.checker.entities.repositories.SimsModDetailsRepository;

@org.springframework.stereotype.Component
public class ConflictRowHighligther extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 6703872492730589499L;
	
	private SimsModDetailsRepository modDetailsRepository;
	
	private static final Logger logger = Logger.getLogger(ConflictRowHighligther.class);
	
	@Autowired
	public ConflictRowHighligther(SimsModDetailsRepository modDetailsRepository) {
		this.modDetailsRepository = modDetailsRepository;
	}
	
    public Component getTableCellRendererComponent(JTable table, 
    		Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        Component cellComponent = super.getTableCellRendererComponent(table, value, 
        		isSelected, hasFocus, row, column);
        
        int modelRowId = table.getRowSorter().convertRowIndexToModel(row);
        
        int Id = (int)table.getModel().getValueAt(modelRowId, 0);
        
        logger.info("Checking for Id:" + Id);
        
        SimsModDetails details = modDetailsRepository.findById(Id).get();
        
        if(details.getModFiles().size() != 1) {
        	cellComponent.setBackground(Color.RED);
        } else if(details.getModFiles().size() == 0) {
        	cellComponent.setBackground(Color.GRAY);
        } else {
        	cellComponent.setBackground(Color.WHITE);
        }
        
        return cellComponent;
    }
}
