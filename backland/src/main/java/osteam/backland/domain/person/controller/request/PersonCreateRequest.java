package osteam.backland.domain.person.controller.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonCreateRequest {
    @NotEmpty
    private String name;
    @NotEmpty
    private String phone;
}
