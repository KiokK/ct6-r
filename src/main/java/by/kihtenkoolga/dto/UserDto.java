package by.kihtenkoolga.dto;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.UUID;

@ToString
@EqualsAndHashCode
public class UserDto {

    public UUID id;

    public String name;

    public String surname;

    public String phone;
}
