package com.microsample.myapp.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.microsample.myapp.domain.Pp_user} entity.
 */
public class Pp_userDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 2)
    private String user_name;

    private LocalDate dob;

    private String role;

    private String location;

    private String about_me;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAbout_me() {
        return about_me;
    }

    public void setAbout_me(String about_me) {
        this.about_me = about_me;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Pp_userDTO pp_userDTO = (Pp_userDTO) o;
        if (pp_userDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pp_userDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Pp_userDTO{" +
            "id=" + getId() +
            ", user_name='" + getUser_name() + "'" +
            ", dob='" + getDob() + "'" +
            ", role='" + getRole() + "'" +
            ", location='" + getLocation() + "'" +
            ", about_me='" + getAbout_me() + "'" +
            "}";
    }
}
