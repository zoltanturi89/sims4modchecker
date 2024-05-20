package hu.diablo.sims4.mod.checker.enitites;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ModFile {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;
	
	String name;
	String extension;
	String location;
	
	@ManyToOne
	SimsModDetails modDetails;
}
