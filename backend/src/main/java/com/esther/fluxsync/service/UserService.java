package com.esther.fluxsync.service;

import com.esther.fluxsync.model.dto.UserDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final DataBaseService db;

    public UserService(DataBaseService db) { this.db = db; }

    @Transactional
    public void registerUser(UserDTO user, String secret) {
        db.update(
                "INSERT INTO users (username, password) VALUES (?, MD5(?))",
                user.getUsername(),
                user.getPassword()
        );

        db.update(
                "INSERT INTO totp_info (username, totp_secret) VALUES (?, ?)",
                user.getUsername(),
                secret
        );
    }

}
