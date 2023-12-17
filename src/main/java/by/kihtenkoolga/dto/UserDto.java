package by.kihtenkoolga.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.UUID;

import static by.kihtenkoolga.validator.UserValidator.PHONE_REGEXP;

@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "UserDto")
public class UserDto {

    public UUID id;

    @NotBlank
    public String name;

    @NotBlank
    public String surname;

    @Pattern(regexp=PHONE_REGEXP, message="Invalid phone number!")
    public String phone;
}
