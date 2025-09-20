package com.esther.fluxsync.controller.user;

import com.esther.fluxsync.ds.UseDB;
import com.esther.fluxsync.model.dto.ResponseDTO;
import com.esther.fluxsync.model.dto.UserDTO;
import com.esther.fluxsync.service.DataBaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
class RegisterController {

    private final DataBaseService db;

    public RegisterController(DataBaseService db) { this.db = db;}

    @PostMapping("/register")
    public String register(@RequestBody UserDTO user) {



        return "ok";
    }

}
