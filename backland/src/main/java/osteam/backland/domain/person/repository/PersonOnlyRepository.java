package osteam.backland.domain.person.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.graphql.data.GraphQlRepository;
import osteam.backland.domain.person.entity.PersonOnly;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@GraphQlRepository
public interface PersonOnlyRepository extends JpaRepository<PersonOnly, UUID> {
    Optional<PersonOnly> findByPhone(String phone);
    List<PersonOnly> findByName(String name);
}
