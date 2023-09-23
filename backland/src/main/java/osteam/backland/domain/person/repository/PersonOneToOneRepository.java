package osteam.backland.domain.person.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import osteam.backland.domain.person.entity.PersonOneToOne;
import osteam.backland.domain.person.entity.PersonOnly;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PersonOneToOneRepository extends JpaRepository<PersonOneToOne, UUID> {

    List<PersonOneToOne> findByName(String name);

}
