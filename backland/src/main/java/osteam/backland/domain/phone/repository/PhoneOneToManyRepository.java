package osteam.backland.domain.phone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import osteam.backland.domain.person.entity.PersonOneToOne;
import osteam.backland.domain.phone.entity.PhoneOneToMany;
import osteam.backland.domain.phone.entity.PhoneOneToOne;

import java.util.Optional;
import java.util.UUID;

public interface PhoneOneToManyRepository extends JpaRepository<PhoneOneToMany, UUID> {
    Optional<PhoneOneToMany> findByPhone(String phone);
}
