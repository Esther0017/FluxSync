package com.esther.fluxsync.controller.user;

import com.esther.fluxsync.ds.UseDB;
import com.esther.fluxsync.model.Status;
import com.esther.fluxsync.model.dto.ResponseDTO;
import com.esther.fluxsync.model.dto.UserDTO;
import com.esther.fluxsync.service.DataBaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
class RegisterController {

    private final DataBaseService db;

    public RegisterController(DataBaseService db) { this.db = db;}

    @PostMapping("/register")
    @UseDB("fluxsync_user")
    @SuppressWarnings("CallToPrintStackTrace")
    public ResponseEntity<ResponseDTO<String>> register(@RequestBody UserDTO user) {
        List<Integer> result = new ArrayList<>();

        try {
            String sql = "SELECT COUNT(*) FROM users WHERE username = ? AND password = MD5(?)";
            result = db.query(sql, new Object[] {user.getUsername(), user.getPassword()}, rs -> rs.getInt(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if  (result.isEmpty() || result.get(0) == 0) {
            ResponseDTO<String> res = new ResponseDTO<>(Status.UNAUTHORIZED, "用户名或密码错误!", null);
            return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
        }

        ResponseDTO<String> res = new ResponseDTO<>(Status.SUCCESS, "登录成功",  null);
        return new ResponseEntity<>(res, HttpStatus.OK);

    }

}
