package com.example.inzynierka.payload;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
@NoArgsConstructor
@ToString
public class RegistrationRequest {
    private String login;
    private String email;
    private String password;
    private String firstName;
    private String lastName;


    public RegistrationRequest(String login, String email, String password, String firstName, String lastName) {
        this.login = login;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
