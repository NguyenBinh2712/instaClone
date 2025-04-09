package com.example.instagram.service;

import com.example.instagram.dto.request.AuthRequest;
import com.example.instagram.dto.request.IntrospectRequest;
import com.example.instagram.dto.response.AuthResponse;
import com.example.instagram.dto.response.IntrospectResponse;
import com.example.instagram.entity.User;
import com.example.instagram.exception.AppException;
import com.example.instagram.exception.ErrorCode;
import com.example.instagram.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimNames;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;
import java.util.StringJoiner;

@Slf4j
@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @NonFinal
    protected static final String signKey="GiiFTXzL8H+f9EYUwYys2N1X2QkdS38cgckeTiN5LX2FC9efQ1mOsD1xAv85O8PO";

    @Autowired
    private PasswordEncoder passwordEncoder;

    public IntrospectResponse introspect(IntrospectRequest request)
            throws JOSEException, ParseException {
        var token=request.getToken();
        JWSVerifier jwsVerifier=new MACVerifier(signKey.getBytes());
        SignedJWT signedJWT=SignedJWT.parse(token);
        Date expTime=signedJWT.getJWTClaimsSet().getExpirationTime();
        var verifier= signedJWT.verify(jwsVerifier);
        return IntrospectResponse.builder()
                .valid(verifier &&expTime.after(new Date()))
                .build();

    }

    public AuthResponse authenticated(AuthRequest authRequest){
        var user=userRepository.findByUsername(authRequest.getUsername())
                .orElseThrow(()->new AppException(ErrorCode.USER_NOT_FOUND));
        boolean authenticated= passwordEncoder.matches(authRequest.getPassword(),user.getPassword());

        if (!authenticated){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        var token=generateToken(user);
        return AuthResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    // tao khoa token
    private String generateToken(User user){
        JWSHeader jwsHeader=new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet=new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("binh.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .claim("scope",buildScope(user))

                .build();
        Payload payload=new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject=new JWSObject(jwsHeader,payload);

        // ki khoa
        try {
            jwsObject.sign(new MACSigner(signKey.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Can not create token !!!",e);
            throw new RuntimeException(e);
        }
    }

    private String buildScope(User user){
        StringJoiner stringJoiner=new StringJoiner(" ");
        if(!CollectionUtils.isEmpty(user.getRoles())){
            user.getRoles().forEach(role->stringJoiner.add(role));
        }
        return stringJoiner.toString();
    }
}
