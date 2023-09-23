package osteam.backland.domain.person.controller.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class PersonResponse {

    @NonNull
    private String name;

    @NonNull
    private String phone;
}
