package mod.checker.backend.checks;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hu.diablo.sims4.mod.checker.enitites.SimsModDetails;
import hu.diablo.sims4.mod.checker.entities.repositories.SimsModDetailsRepository;

@Component
public class CheckVersions {
	private SimsModDetailsRepository modDetailsRepository;
	
	@Autowired
	public CheckVersions(SimsModDetailsRepository modDetailsRepository) {
		this.modDetailsRepository = modDetailsRepository;
	}
	
	public void checkVersions() {
		List<SimsModDetails> markedForRemoval = new ArrayList<SimsModDetails>();
		List<SimsModDetails> markedToAdd = new ArrayList<SimsModDetails>();
		modDetailsRepository.findAll().iterator().forEachRemaining(item ->{
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
			modDetailsRepository.delete(item);
		});
		
		markedToAdd.stream().forEach(item -> {
			modDetailsRepository.save(item);
		});
		
	}
	
	private List<SimsModDetails> findDifferentVersions(SimsModDetails details){
		List<SimsModDetails> returnValue = new ArrayList<SimsModDetails>();
		
		Iterable<SimsModDetails> sameModNames = modDetailsRepository.findByName(details.getModName());
		
		List<SimsModDetails> matches = StreamSupport.stream(sameModNames.spliterator(), false).filter(item -> {
			return item.getModName().equals(details.getModName())
					&& item.getId() != details.getId();
		}).collect(Collectors.toList());
		
		returnValue.addAll(matches);
		
		return returnValue;
	}
}
