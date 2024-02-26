package com.timesync.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.timesync.authentication.User.User;
import com.timesync.authentication.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;

    @Autowired
    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User verifyJWT(String token){
        Algorithm algorithm = Algorithm.HMAC256("timesync");

        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("timesync")
                .build();
        DecodedJWT decodedJWT = verifier.verify(token);
        Claim claim = decodedJWT.getClaim("id");
        Long id = claim.asLong();

        return userRepository.findById(id).get(); // TODO: eror check
    }

    public String generateJWT(User user){
        Algorithm algorithm = Algorithm.HMAC256("timesync");
        String jwtToken = JWT.create()
                .withIssuer("timesync")
                .withClaim("email", user.getEmail())
                .withClaim("id", user.getId())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 3600L*1000L))
                .withJWTId(UUID.randomUUID()
                        .toString())
                .withNotBefore(new Date(System.currentTimeMillis() + 1000L))
                .sign(algorithm);

        return jwtToken;
    }


}
