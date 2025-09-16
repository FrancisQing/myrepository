package cn.kryex.phonerepair.controller;

import cn.kryex.phonerepair.entity.common.PageResult;
import cn.kryex.phonerepair.entity.common.Result;
import cn.kryex.phonerepair.entity.dto.UserDTO;
import cn.kryex.phonerepair.entity.dto.UserQuery;
import cn.kryex.phonerepair.entity.vo.UserVO;
import cn.kryex.phonerepair.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/getAllUsers")
    public Result<PageResult<UserVO>> getAllUsers(UserQuery userQuery) {
        PageResult<UserVO> result = userService.list(userQuery);
        return Result.success(result);
    }

    @RequestMapping("updateUser")
    public Result<Boolean> updateUser(@RequestBody UserDTO userDTO) {
        userService.updateUserById(userDTO);
        return Result.success(null);
    }
}