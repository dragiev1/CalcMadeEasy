package com.calcmadeeasy.services;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Collections;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.calcmadeeasy.dto.AuthResponse;
import com.calcmadeeasy.models.Users.User;
import com.calcmadeeasy.repository.UserRepo;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepo userRepo;

    // Inject Google client ID from application.yml
    @Value("${google.client.id}")
    private String googleClientId;

    @Value("${jwt.secret}")
    private String jwtSecretString;

    private Key getSigningKey() {
        byte[] keyBytes = jwtSecretString.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Transactional
    public AuthResponse loginWithGoogle(String idTokenString) {
        try {

            // Debugging:
            System.out.println("BACKEND CLIENT ID: " + googleClientId);

            // Verify token with Google.
            // Analogy: Bouncer at a nightclub.
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                new NetHttpTransport(),   // UV Light
                GsonFactory.getDefaultInstance()  // Magnifying Glass
            )
            .setAudience(Collections.singletonList(googleClientId))  // Bouncer checking ID(s).
            .build();

            // Driver's license. Has name, photo, etc. 
            GoogleIdToken idToken = verifier.verify(idTokenString);  

            // If license is fake, bouncer throws you out; lol. 
            if(idToken == null) throw new IllegalArgumentException("Invalid ID Token.");

            // If bouncer lets you in... then
            // Extract payload (Basically the information we want after verification).
            GoogleIdToken.Payload payload = idToken.getPayload();

            String googleId = payload.getSubject();  // Unique 'sub.'
            String email = payload.getEmail();
            String firstName = (String) payload.get("given_name");
            String lastName = (String) payload.get("family_name");
            String picture = (String) payload.get("picture");

            User user = userRepo.findByGoogleId(googleId)  // Check if user exists.
                .orElseGet(() -> {
                    // If user doesn't exist, create a new User.
                    User newUser = User.builder()
                        .googleId(googleId)
                        .email(email)
                        .firstName(firstName)
                        .lastName(lastName)
                        .profilePicUrl(picture)
                        .build();
                    return userRepo.save(newUser);
                });

            // But this nightclub is weird, they take your license and gives you a special wristband. You have 24 hours max to be there. 
            // Generate app's JWT
            String appJWT = Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("email", user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))  // 24 hours
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)  // This is the secret ink bouncer uses on the wristband to prevent bad actors (hackers) from forging a wristband to get in.
                .compact();
            
            // Return response
            return new AuthResponse(appJWT, user);


        } catch (Exception e) {
            throw new RuntimeException("Failed to authenticate with Google", e);
        }
    }
}
