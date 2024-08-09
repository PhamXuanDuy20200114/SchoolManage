package com.example.schoolmanage.service;

import com.example.schoolmanage.dto.request.AuthenticateRequest;
import com.example.schoolmanage.dto.request.RefreshTokenRequest;
import com.example.schoolmanage.dto.response.AuthenticateResponse;
import com.example.schoolmanage.entity.Role;
import com.example.schoolmanage.entity.User;
import com.example.schoolmanage.exception.AppException;
import com.example.schoolmanage.exception.ErrorCode;
import com.example.schoolmanage.repository.RoleRepository;
import com.example.schoolmanage.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Set;
import java.util.StringJoiner;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticateService {

    @NonFinal
    @Value("${jwt.signedKey}")
    String SIGNED_KEY;

    @NonFinal
    @Value("${jwt.valid-duration}")
    int VALID_DURATION;

    @NonFinal
    @Value("${jwt.refresh-duration}")
    int REFRESH_DURATION;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public AuthenticateResponse login(AuthenticateRequest request) throws ParseException, JOSEException {
        User user = userRepository.findByEmail(request.getEmail());
        if (user == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!authenticated) {
            throw new AppException(ErrorCode.PASSWORD_INVALID);
        }

        try {
            var token = generateToken(user);
            user.setToken(token);
            userRepository.save(user);
            return AuthenticateResponse.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .roles(user.getRoles())
                    .token(token)
                    .build();
        } catch (KeyLengthException e) {
            throw new RuntimeException(e);
        }

    }

    public String register(AuthenticateRequest request) throws ParseException, JOSEException {
        Set<String> roleNames = request.getRoles();
        Set<Role> roles = (Set<Role>) roleRepository.findAllById(roleNames);
        User user = new User().builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(roles)
                .build();
        userRepository.save(user);
        return "Register successful";
    }

    public String logout() throws ParseException, JOSEException {
        var context = SecurityContextHolder.getContext();
        var email = context.getAuthentication().getName();
        User user = userRepository.findByEmail(email);
        user.setToken("");
        userRepository.save(user);
        return "Log out success!";
    }

    public String refreshToken(RefreshTokenRequest request) throws ParseException, JOSEException {
        SignedJWT signedJWT = verifyToken(request.getToken(), true);
        User user = userRepository.findByEmail(signedJWT.getJWTClaimsSet().getSubject());
        String refreshToken = generateToken(user);
        user.setToken(refreshToken);
        userRepository.save(user);
        return refreshToken;
    }

    SignedJWT verifyToken(String token, boolean isRefresh) throws ParseException, JOSEException {
        JWSVerifier verifier = new MACVerifier(SIGNED_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        Date expityTime = (isRefresh)
                ? new Date(signedJWT.getJWTClaimsSet().getIssueTime()
                .toInstant().plus(REFRESH_DURATION, ChronoUnit.SECONDS).toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();
        var verified = signedJWT.verify(verifier);
        if(!(verified && expityTime.after(new Date()))){
            throw new AppException(ErrorCode.EXPIRED_TOKEN);
        }
        return signedJWT;
    }

    String generateToken(User user) throws KeyLengthException {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .jwtID(UUID.randomUUID().toString())
                .subject(user.getEmail())
                .issuer("duypham")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now()
                        .plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()))
                .claim("scope", buildScope(user))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toString());
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
        try {
            jwsObject.sign(new MACSigner(SIGNED_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new RuntimeException(e);
        }
    }

    String buildScope (User user){
        StringJoiner stringJoiner = new StringJoiner(" ");
        if(!CollectionUtils.isEmpty(user.getRoles())){
            user.getRoles().forEach(role -> {
                stringJoiner.add("ROLE_"+role.getName());
            });
        }
        return stringJoiner.toString();
    }


}
