package com.esther.fluxsync.controller.user;

import com.esther.fluxsync.ds.UseDB;
import com.esther.fluxsync.model.Status;
import com.esther.fluxsync.model.dto.ResponseDTO;
import com.esther.fluxsync.model.dto.UserDTO;
import com.esther.fluxsync.service.DataBaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
class LoginController {

    private final DataBaseService db;

    public LoginController(DataBaseService db) { this.db = db; }

    /**
     * 用户登录接口，验证用户名和密码，并返回相应的响应状态。
     * <p>
     * 该接口接收一个包含用户名和密码的用户对象，查询数据库以验证用户凭据是否正确。
     * 若凭据正确，返回成功响应；若凭据错误，返回未授权响应。
     * </p>
     *
     * @param user 包含用户登录信息的 {@link UserDTO} 对象，包含用户名和密码
     * @return 包含登录结果的 {@link ResponseEntity}，携带 {@link ResponseDTO} 对象
     *         - 若登录成功，状态为200 OK，消息为“登录成功”
     *         - 若登录失败，状态为401 Unauthorized，消息为“用户名或密码错误”
     *
     */
    @PostMapping("/login")
    @UseDB("fluxsync_user")
    @SuppressWarnings("CallToPrintStackTrace")
    public ResponseEntity<ResponseDTO<String>> login(@RequestBody UserDTO user) {
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
