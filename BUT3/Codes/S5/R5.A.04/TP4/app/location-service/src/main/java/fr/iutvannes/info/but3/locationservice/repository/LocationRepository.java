package fr.iutvannes.info.but3.locationservice.repository;

import fr.iutvannes.info.but3.locationservice.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {}
