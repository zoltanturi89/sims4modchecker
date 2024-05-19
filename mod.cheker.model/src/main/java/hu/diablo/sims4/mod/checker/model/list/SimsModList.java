package hu.diablo.sims4.mod.checker.model.list;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hu.diablo.sims4.mod.checker.model.SimsModDetails;

public class SimsModList {
	List<SimsModDetails> simsModList;
	
	public List<SimsModDetails> getSimsModList() {
		return Collections.unmodifiableList(this.simsModList);
	}
	
	public void addModToModList(SimsModDetails details) {
		if(this.simsModList == null) {
			this.simsModList = new ArrayList<>();
		}
		
		this.simsModList.add(details);
	}
	
	public void removeFromModList(SimsModDetails details) {
		simsModList.remove(details);
	}
}
