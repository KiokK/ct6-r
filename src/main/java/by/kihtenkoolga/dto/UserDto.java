package by.kihtenkoolga.dto;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.UUID;

@ToString
@EqualsAndHashCode
@XmlRootElement(name = "UserDto")
public class UserDto {

    public UUID id;

    public String name;

    public String surname;

    public String phone;
}
