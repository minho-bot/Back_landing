package osteam.backland.domain.phone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import osteam.backland.domain.person.entity.PersonOneToOne;
import osteam.backland.domain.phone.entity.PhoneOneToOne;

import java.util.Optional;
import java.util.UUID;

public interface PhoneOneToOneRepository extends JpaRepository<PhoneOneToOne, UUID> {
    Optional<PhoneOneToOne> findByPhone(String phone);
}
