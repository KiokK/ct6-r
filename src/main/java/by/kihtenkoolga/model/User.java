package by.kihtenkoolga.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private UUID id;

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @Pattern(regexp="^\\s*\\+?375(25|29|33|34)(\\d{7})", message="Invalid phone number!")
    private String phone;

    private LocalDate createdAt;

}
