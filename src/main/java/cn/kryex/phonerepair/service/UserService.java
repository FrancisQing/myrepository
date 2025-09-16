package cn.kryex.phonerepair.service;

import cn.kryex.phonerepair.entity.common.PageResult;
import cn.kryex.phonerepair.entity.dto.LoginUser;
import cn.kryex.phonerepair.entity.dto.RegisterUser;
import cn.kryex.phonerepair.entity.dto.UserDTO;
import cn.kryex.phonerepair.entity.dto.UserQuery;
import cn.kryex.phonerepair.entity.po.User;
import cn.kryex.phonerepair.entity.vo.UserVO;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserService extends IService<User> {
    LoginUser login(String username0rEmail, String userPassword);
    boolean register(RegisterUser registerUser);
    User selectByUserId(Integer userId);

    PageResult<UserVO> list(UserQuery userQuery);
    void updateUserById(UserDTO userDTO);
}