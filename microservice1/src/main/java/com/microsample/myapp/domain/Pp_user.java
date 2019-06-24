package com.microsample.myapp.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Pp_user.
 */
@Entity
@Table(name = "pp_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Pp_user implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 2)
    @Column(name = "user_name", nullable = false)
    private String user_name;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "role")
    private String role;

    @Column(name = "location")
    private String location;

    @Column(name = "about_me")
    private String about_me;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public Pp_user user_name(String user_name) {
        this.user_name = user_name;
        return this;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public LocalDate getDob() {
        return dob;
    }

    public Pp_user dob(LocalDate dob) {
        this.dob = dob;
        return this;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getRole() {
        return role;
    }

    public Pp_user role(String role) {
        this.role = role;
        return this;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getLocation() {
        return location;
    }

    public Pp_user location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAbout_me() {
        return about_me;
    }

    public Pp_user about_me(String about_me) {
        this.about_me = about_me;
        return this;
    }

    public void setAbout_me(String about_me) {
        this.about_me = about_me;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pp_user)) {
            return false;
        }
        return id != null && id.equals(((Pp_user) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Pp_user{" +
            "id=" + getId() +
            ", user_name='" + getUser_name() + "'" +
            ", dob='" + getDob() + "'" +
            ", role='" + getRole() + "'" +
            ", location='" + getLocation() + "'" +
            ", about_me='" + getAbout_me() + "'" +
            "}";
    }
}
