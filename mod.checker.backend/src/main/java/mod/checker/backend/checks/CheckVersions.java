package mod.checker.backend.checks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import hu.diablo.sims4.mod.checker.model.SimsModDetails;
import hu.diablo.sims4.mod.checker.model.list.SimsModList;

public class CheckVersions {
	SimsModList modList;
	
	public CheckVersions(SimsModList mods) {
		this.modList = mods;
	}
	
	public void checkVersions() {
		List<SimsModDetails> markedForRemoval = new ArrayList<SimsModDetails>();
		List<SimsModDetails> markedToAdd = new ArrayList<SimsModDetails>();
		modList.getSimsModList().stream().forEach(item ->{
			List<SimsModDetails> versions = findDifferentVersions(item);
			
			Optional<SimsModDetails> modDetails = versions.stream().reduce((SimsModDetails first,SimsModDetails second) ->{
				SimsModDetails retVal = new SimsModDetails(
						Math.min(first.getId(),second.getId()),
						first.getModName(),first.getModType());
				
				List<String> modFiles = new ArrayList<String>();
				modFiles.addAll(first.getModFiles());
				modFiles.addAll(second.getModFiles());
				
				retVal.setModFiles(modFiles);
				
				markedForRemoval.add(first);
				markedForRemoval.add(second);
				
				return retVal;
			});
			
			if(modDetails.isPresent()) {
				markedToAdd.add(modDetails.get());
			}
		});
		
		markedForRemoval.stream().forEach(item -> {
			modList.removeFromModList(item);
		});
		
		markedToAdd.stream().forEach(item -> {
			modList.addModToModList(item);
		});
		
	}
	
	private List<SimsModDetails> findDifferentVersions(SimsModDetails details){
		List<SimsModDetails> returnValue = new ArrayList<SimsModDetails>();
		
		List<SimsModDetails> matches = this.modList.getSimsModList().stream().filter(item -> {
			return item.getModName().equals(details.getModName())
					&& item.getId() != details.getId();
		}).collect(Collectors.toList());
		
		returnValue.addAll(matches);
		
		return returnValue;
	}
}
