package com.esther.fluxsync.controller.user;

import com.esther.fluxsync.ds.UseDB;
import com.esther.fluxsync.model.Status;
import com.esther.fluxsync.model.dto.ResponseDTO;
import com.esther.fluxsync.model.dto.UserDTO;
import com.esther.fluxsync.service.DataBaseService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
class LoginController {

    private final DataBaseService db;

    public LoginController(DataBaseService db) { this.db = db; }

    @Value("${app.services.jwt.url}")
    public String jwtUrl;

    @Value("${app.services.jwt.uri}")
    public String jwtUri;

    /**
     * 用户登录接口，验证用户名和密码，并通过外部 JWT 服务生成认证 token。
     * <p>
     * 该接口接收用户登录信息，通过查询数据库验证用户名和密码的正确性，若成功，
     * 则通过外部服务请求生成一个 JWT token，返回给客户端。
     * 若验证失败，则返回用户名或密码错误的响应。
     * </p>
     *
     * @param user 包含登录信息的 {@link UserDTO} 对象，包含用户名和密码
     * @return {@link ResponseEntity} 包含 {@link ResponseDTO} 对象，成功时携带生成的 JWT token
     *         - 登录成功：状态200 OK，消息“登录成功”，响应体中携带 token
     *         - 登录失败：状态401 Unauthorized，消息“用户名或密码错误”
     *         - 登录失败（外部服务问题）：状态502 Bad Gateway，消息“登录失败”
     *
     */
    @PostMapping("/login")
    @UseDB("fluxsync_user")
    public ResponseEntity<ResponseDTO<?>> login(@RequestBody UserDTO user) {
        List<Integer> result = db.query(
                "SELECT COUNT(*) FROM users WHERE username = ? AND password = MD5(?)",
                rs -> rs.getInt(1),
                user.getUsername(),
                user.getPassword()
        );

        if  (result.isEmpty() || result.get(0) == 0) {
            ResponseDTO<String> res = new ResponseDTO<>(Status.UNAUTHORIZED, "用户名或密码错误!", null);
            return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
        }

        class jwtJson {
            public final String username;
            public jwtJson(String username) { this.username = username; }
        }
        WebClient client = WebClient.create(jwtUrl);

        record TokenRes(String status, String token) {}
        TokenRes token = client.post()
                .uri(jwtUri)
                .bodyValue(new jwtJson(user.getUsername()))
                .retrieve()
                .bodyToMono(TokenRes.class)
                .block();

        record Res(String token, Boolean totpEnable) {}
        ResponseDTO<Res> res = null;

        if (token != null) {

            List<Boolean> enable = db.query(
                    "SELECT totp_enable FROM totp_info WHERE username = ?",
                    rs -> rs.getBoolean(1),
                    user.getUsername()
            );

            boolean isTotpEnabled = !enable.isEmpty() && enable.get(0);

            res = new ResponseDTO<>(Status.SUCCESS, "登录成功",  new Res(token.token, isTotpEnabled));
            return new ResponseEntity<>(res, HttpStatus.OK);
        }

        res = new ResponseDTO<>(Status.ERROR, "登录失败!", null);
        return new ResponseEntity<>(res, HttpStatus.BAD_GATEWAY);
    }


}
