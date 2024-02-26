package com.timesync.authentication.User;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;

@Entity
@Table
public class User {
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long id;
    private String name;
    private String email;
    @JsonIgnore
    private String passwordDigest;
    @Transient
    @JsonDeserialize
    private String password;

    public void setPasswordDigest(String passwordDigest) {
        this.passwordDigest = passwordDigest;
    }

    public String getPasswordDigest() {
        return passwordDigest;
    }

    public void generatePasswordDigest(){
        String bcryptHashString = BCrypt.withDefaults().hashToString(12, password.toCharArray());
        setPasswordDigest(bcryptHashString);
    }

    public boolean verify(String pass){
        BCrypt.Result result = BCrypt.verifyer().verify(pass.toCharArray(), passwordDigest);
        return result.verified;
    }

    public String _getPassword(){
        return password;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public User(){}

    public User(Long id, String name, String email, String passowrd_digest) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.passwordDigest = passowrd_digest;
    }
    public User(String name, String email, String passowrd_digest) {
        this.name = name;
        this.email = email;
        this.passwordDigest = passowrd_digest;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", passwordDigest='" + passwordDigest + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
