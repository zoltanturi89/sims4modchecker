package hu.diablo.sims4.mod.checker.enitites;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "sims_mod_details")
public class SimsModDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String modName;
	
	@Enumerated(EnumType.STRING)
	private ModType modType;
	
	private List<String> modFiles;
	
	public SimsModDetails() {}

	public SimsModDetails(int id, String modName, ModType modType) {
		this.id = id;
		this.modName = modName;
		this.modType = modType;
	}
	
	public ModType getModType() {
		return modType;
	}
	
	public void setModType(ModType modType) {
		this.modType = modType;
	}
	
	public List<String> getModFiles() {
		return modFiles;
	}
	
	public void setModFiles(List<String> modFiles) {
		this.modFiles = modFiles;
	}
	
	public void addModFile(String modFile) {
		this.modFiles.add(modFile);
	}
	
	public String getModFilesAsString() {
		
		StringBuilder arr = new StringBuilder();
		arr.append("[");
		for(String modFile : modFiles) {
			arr.append(modFile);
			arr.append(",");
		}
		arr.deleteCharAt(arr.length()-1);
		arr.append("]");
		
		return arr.toString();
	}
	
	public String getModName() {
		return this.modName;
	}
	
	public void setModName(String modName) {
		this.modName = modName;
	}
	
	public int getId() {
		return id; 
	}
	
	public void setId(int id) {
		this.id = id;
	}
}