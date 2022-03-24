package com.admiralxy.springaccounts.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "credentials")
@Getter
@Setter
@ToString
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

    @Transient
    private boolean isDecrypted = false;

    public Credential() {

    }

    public Credential(String name, String login, String password, String securityKey, User user) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.securityKey = securityKey;
        this.user = user;
    }
}
