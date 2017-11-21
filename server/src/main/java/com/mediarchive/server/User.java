package com.mediarchive.server;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    public static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    private String name;
    private @JsonIgnore String password;

    @ElementCollection
    private Map<String, Object> mediaMap;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = PASSWORD_ENCODER.encode(password);
    }

    public <T extends Media> void addMedia(T m) {
        if (mediaMap == null) {
            mediaMap = new HashMap<>();
        }
        if (!mediaMap.containsKey(m.getId())) {
            mediaMap.put(m.getId(), m);
        }
    }

    public Map<String, Object> getMedia() {
        return mediaMap;
    }
}
