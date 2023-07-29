package osteam.backland.domain.person.controller.response;

import lombok.Data;
import lombok.NonNull;

@Data
public class PersonResponse {

    @NonNull
    private String name;

    @NonNull
    private String phone;
}
