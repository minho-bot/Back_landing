package osteam.backland.domain.person.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import osteam.backland.domain.person.entity.PersonOneToMany;
import osteam.backland.domain.person.entity.PersonOnly;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PersonOneToManyRepository extends JpaRepository<PersonOneToMany, UUID> {
    Optional<PersonOneToMany> findByName(String name);
}
