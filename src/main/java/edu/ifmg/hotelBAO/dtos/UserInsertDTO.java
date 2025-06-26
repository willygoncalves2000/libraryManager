package edu.ifmg.hotelBAO.dtos;

import edu.ifmg.hotelBAO.entities.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

public class UserInsertDTO extends UserDTO{
    @NotBlank
    @Size(min = 2, max = 50)
    private String password;

    public UserInsertDTO() {
        super();
    }

    public UserInsertDTO(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}