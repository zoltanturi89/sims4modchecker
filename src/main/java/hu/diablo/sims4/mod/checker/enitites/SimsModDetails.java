package hu.diablo.sims4.mod.checker.enitites;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "sims_mod_details")
@Getter
@Setter
public class SimsModDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String modName;
	
	@Enumerated(EnumType.STRING)
	private ModType modType;
	
	@OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
	private List<ModFile> modFiles;
	
	public SimsModDetails() {}

	public SimsModDetails(int id, String modName, ModType modType) {
		this.id = id;
		this.modName = modName;
		this.modType = modType;
	}
	
	public String getModFilesAsString() {
		
		StringBuilder arr = new StringBuilder();
		arr.append("[");
		for(ModFile modFile : modFiles) {
			arr.append(modFile.getName());
			arr.append(",");
		}
		arr.deleteCharAt(arr.length()-1);
		arr.append("]");
		
		return arr.toString();
	}
	
}
