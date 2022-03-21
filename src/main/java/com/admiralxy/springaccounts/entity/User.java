package com.admiralxy.springaccounts.entity;

import com.admiralxy.springaccounts.validation.Matches;
import com.admiralxy.springaccounts.validation.UniqueUser;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@UniqueUser
@Matches(field = "password", verifyField = "passwordConfirmation")
public class User extends BaseEntity {
    @Size(min=4, max=25, message = "{validation.size.string}")
    @NotNull(message = "{validation.empty}")
    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Transient
    private String passwordConfirmation;

    @Column(name = "active")
    private boolean active;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;
}
