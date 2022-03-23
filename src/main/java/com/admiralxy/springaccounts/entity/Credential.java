package com.admiralxy.springaccounts.entity;

import com.admiralxy.springaccounts.validation.Matches;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "credentials")
@Getter
@Setter
public class Credential extends BaseEntity {
    @Size(min=1, max=100, message = "{validation.size.string}")
    @NotNull(message = "{validation.empty}")
    @Column(name = "name")
    private String name;

    @Size(min=1, max=100, message = "{validation.size.string}")
    @NotNull(message = "{validation.empty}")
    @Column(name = "login")
    private String login;

    @Size(min=1, max=1000, message = "{validation.size.string.limit}")
    @Column(name = "password")
    private String password;

    @Transient
    @NotNull(message = "{validation.empty}")
    @Size(min=1, max=100, message = "{validation.size.string}")
    private String securityKey;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
