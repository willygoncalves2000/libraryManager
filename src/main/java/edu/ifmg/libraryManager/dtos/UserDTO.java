package edu.ifmg.libraryManager.dtos;

import edu.ifmg.libraryManager.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;

public class UserDTO {

    private Long id;
    @NotNull(message = "Campo obrigatório")
    private String name;
    @Email(message = "Favor entrar com um email válido")
    private String email;
    private String phone;
    Set<RoleDTO> roles = new HashSet<>();

    public UserDTO() {}

    public UserDTO(Long id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public UserDTO(User entity) {
        id = entity.getId();
        name = entity.getName();
        email = entity.getEmail();
        phone = entity.getPhone();
        entity.getRoles().forEach(role -> this.roles.add(new RoleDTO(role)));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Set<RoleDTO> getRoles() {
        return roles;
    }


    public void setRoles(Set<RoleDTO> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
