package osteam.backland.domain.person.controller.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
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
    @Size(min = 11, max = 11, message = "번호는 11자리 이어야 합니다.")
    private String phone;
}
