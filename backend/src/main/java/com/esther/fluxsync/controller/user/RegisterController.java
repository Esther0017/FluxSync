package com.esther.fluxsync.controller.user;

import com.esther.fluxsync.ds.UseDB;
import com.esther.fluxsync.model.Status;
import com.esther.fluxsync.model.dto.ResponseDTO;
import com.esther.fluxsync.model.dto.UserDTO;
import com.esther.fluxsync.service.DataBaseService;
import com.esther.fluxsync.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/user")
class RegisterController {

    private final DataBaseService db;
    private final UserService userService;

    public RegisterController(DataBaseService db, UserService userService) {
        this.db = db;
        this.userService = userService;
    }

    @Value("${app.services.totp.url}")
    private String totpUrl;

    /**
     * 用户注册接口，用于注册新用户并生成TOTP密钥。
     * <p>
     * 该接口接收一个包含用户注册信息的用户对象，调用外部TOTP服务生成密钥，
     * 然后通过调用 {@link UserService#registerUser(UserDTO, String)} 方法完成用户的注册过程。
     * 注册成功则返回成功响应，注册失败则返回错误响应。
     * </p>
     *
     * @param user 包含用户注册信息的 {@link UserDTO} 对象
     * @return 包含注册结果的 {@link ResponseEntity}，携带 {@link ResponseDTO} 对象
     *         - 若注册成功，状态为200 OK，消息为“注册成功”
     *         - 若注册失败，状态为400 Bad Request，消息为“注册失败: 错误信息”
     *
     */
    @PostMapping("/register")
    @UseDB("fluxsync_user")
    public ResponseEntity<ResponseDTO<String>> register(@RequestBody UserDTO user) {

        WebClient client = WebClient.create(totpUrl);
        String secret = client.get()
                .uri("/generateSecret")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        try {
            userService.registerUser(user, secret);
            return ResponseEntity.ok(new ResponseDTO<>(Status.SUCCESS, "注册成功", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO<>(Status.ERROR, "注册失败: " + e.getMessage(), null));
        }

    }

}
