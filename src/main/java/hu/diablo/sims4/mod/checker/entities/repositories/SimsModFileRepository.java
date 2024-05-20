package hu.diablo.sims4.mod.checker.entities.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.diablo.sims4.mod.checker.enitites.ModFile;

public interface SimsModFileRepository extends JpaRepository<ModFile, Integer> {

}
