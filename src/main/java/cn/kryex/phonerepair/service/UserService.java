package cn.kryex.phonerepair.service;

import cn.kryex.phonerepair.entity.dto.LoginUser;
import cn.kryex.phonerepair.entity.dto.RegisterUser;
import cn.kryex.phonerepair.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserService extends IService<User> {
    LoginUser login(String username0rEmail, String userPassword);
    boolean register(RegisterUser registerUser);

    User selectByUserId(Integer userId);
}