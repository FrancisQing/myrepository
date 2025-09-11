package cn.kryex.phonerepair.controller;

import cn.kryex.phonerepair.entity.dto.LoginUser;
import cn.kryex.phonerepair.entity.dto.RegisterUser;
import cn.kryex.phonerepair.entity.common.Result;
import cn.kryex.phonerepair.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    //接收参数
    @PostMapping("/login")
    public Result<LoginUser> login(@RequestParam(value = "usernameOrEmail") String usernameOrEmail,
                                   @RequestParam(value = "password") String password) {
        LoginUser loginUser = userService.login(usernameOrEmail, password);
        if (loginUser == null)
            return Result.fail("用户名或密码错误",400);
        return Result.success(loginUser);
    }

    @PostMapping("/register")
    public Result<String> register(@RequestBody RegisterUser registerUser) {
        try {
            boolean success = userService.register(registerUser);
            if (success) {
                return Result.success("注册成功");
            } else {
                return Result.fail("注册失败",500);
            }
        } catch (RuntimeException e) {
            return Result.fail(e.getMessage(),400);
        }
    }
}