package com.mineplex.service.common.worker;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.function.Consumer;

@ApplicationScoped
public class JwtWorker {

    private static final String ISSUER = "mineplex-kotlar-security";

    @ConfigProperty(name = "JWT_SECRET")
    private String jwtSecret;

    private Algorithm algorithm = null;
    private JWTVerifier jwtVerifier = null;

    public String encode(Consumer<JWTCreator.Builder> callback) {
        JWTCreator.Builder builder = JWT.create()
                .withIssuer(ISSUER);

        if (callback != null) callback.accept(builder);

        return builder.sign(getAlgorithm());
    }

    public boolean verify(String token) {
        try {
            getJwtVerifier().verify(token);

            return true;
        } catch (Throwable throwable) {
            return false;
        }
    }

    public DecodedJWT decode(String token) {
        return getJwtVerifier().verify(token);
    }

    private Algorithm getAlgorithm() {
        if (algorithm == null)
            algorithm = Algorithm.HMAC256(getJwtSecret());

        return algorithm;
    }

    public JWTVerifier getJwtVerifier() {
        if (jwtVerifier == null)
            jwtVerifier = JWT.require(getAlgorithm())
                    .withIssuer(ISSUER)
                    .build();

        return jwtVerifier;
    }

    public String getJwtSecret() {
        return jwtSecret;
    }
}
