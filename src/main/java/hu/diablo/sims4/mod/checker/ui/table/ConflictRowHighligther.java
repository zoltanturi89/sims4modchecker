package hu.diablo.sims4.mod.checker.ui.table;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import org.springframework.beans.factory.annotation.Autowired;

import hu.diablo.sims4.mod.checker.enitites.SimsModDetails;
import hu.diablo.sims4.mod.checker.entities.repositories.SimsModDetailsRepository;

@org.springframework.stereotype.Component
public class ConflictRowHighligther extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 6703872492730589499L;
	
	private SimsModDetailsRepository modDetailsRepository;
	
	@Autowired
	public ConflictRowHighligther(SimsModDetailsRepository modDetailsRepository) {
		this.modDetailsRepository = modDetailsRepository;
	}
	
    public Component getTableCellRendererComponent(JTable table, 
    		Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        Component cellComponent = super.getTableCellRendererComponent(table, value, 
        		isSelected, hasFocus, row, column);
        
        SimsModDetails details = modDetailsRepository.findById(row-1).get();
        
        if(details.getModFiles().size() > 1) {
        	cellComponent.setBackground(Color.RED);
        } else {
        	cellComponent.setBackground(Color.WHITE);
        }
        
        return cellComponent;
    }
}
