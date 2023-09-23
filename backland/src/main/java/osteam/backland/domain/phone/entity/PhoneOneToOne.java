package osteam.backland.domain.phone.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import osteam.backland.domain.person.entity.PersonOneToOne;
import osteam.backland.global.entity.PrimaryKeyEntity;

@Entity
@Getter
@Setter
public class PhoneOneToOne extends PrimaryKeyEntity {

    private String phone;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_user_id", referencedColumnName = "id")
    private PersonOneToOne personOneToOne;
}
