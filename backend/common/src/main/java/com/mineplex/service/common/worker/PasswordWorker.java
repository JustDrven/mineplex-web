package com.mineplex.service.common.worker;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PasswordWorker {

    private final Argon2 argon2;

    @ConfigProperty(name = "ARGON_SALT")
    private String passwordSalt;

    public PasswordWorker() {
        argon2 = Argon2Factory.create();
    }

    public String hash(String password) {
        return argon2.hash(
                10,
                65536,
                1,

                salt(password).toCharArray()
        );
    }

    public boolean verify(String hashPassword, String password) {
        return argon2.verify(hashPassword, salt(password).getBytes());
    }

    private String salt(String value) {
        return "%s-%s".formatted(passwordSalt, value);
    }


}
