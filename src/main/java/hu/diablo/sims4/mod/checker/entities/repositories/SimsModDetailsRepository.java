package hu.diablo.sims4.mod.checker.entities.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.diablo.sims4.mod.checker.enitites.SimsModDetails;

public interface SimsModDetailsRepository extends JpaRepository<SimsModDetails, Integer> {
	
	public Iterable<SimsModDetails> findByModName(String modName);
}
