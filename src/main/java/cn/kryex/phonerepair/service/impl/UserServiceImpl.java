package cn.kryex.phonerepair.service.impl;

import cn.kryex.phonerepair.entity.dto.LoginUser;
import cn.kryex.phonerepair.entity.User;
import cn.kryex.phonerepair.entity.dto.RegisterUser;
import cn.kryex.phonerepair.mapper.UserMapper;
import cn.kryex.phonerepair.service.UserService;
import cn.kryex.phonerepair.utils.Md5Password;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public LoginUser login(String username0rEmail, String userPassword) {
        User user = this.getOne(new QueryWrapper<User>()
                .eq("user_name", username0rEmail)
                .or()
                .eq("user_email", username0rEmail));
        if (user == null) {
            return null;
        }
        if (!user.getUserPasswordHash().equals(Md5Password.generateMD5(userPassword))) {
            return null;
        }
        LoginUser loginUser = new LoginUser();
        loginUser.setUserId(user.getUserId());
        loginUser.setUserName(user.getUserName());
        loginUser.setUserEmail(user.getUserEmail());
        loginUser.setRoleId(user.getRoleId());
        loginUser.setUserBio(user.getUserBio());
        loginUser.setUserPhone(user.getUserPhone());
        return loginUser;
    }

    @Override
    public boolean register(RegisterUser registerUser) {
        if(registerUser.getUserName()==null || registerUser.getUserEmail()==null || registerUser.getUserPasswordHash()==null){
            throw new RuntimeException("参数不能为空");
        }
        User user = this.getOne(new QueryWrapper<User>()
                .eq("user_name", registerUser.getUserName())
                .or()
                .eq("user_email", registerUser.getUserEmail()));
        if (user != null) {
            throw new RuntimeException("用户名或邮箱已存在");
        }
        if(registerUser.getUserPasswordHash().length()<6 || registerUser.getUserPasswordHash().matches(".* [a-zA-Z]+.*")){
            throw new RuntimeException("密码长度至少6位且必须包含字母");
        }
        User newUser = new User();
        newUser.setUserName(registerUser.getUserName());
        newUser.setUserEmail(registerUser.getUserEmail());
        newUser.setUserPasswordHash(Md5Password.generateMD5(registerUser.getUserPasswordHash()));
        newUser.setRoleId("2");
        return this.save(newUser);
    }

    @Override
    public User selectByUserId(Integer userId) {
        return userMapper.selectByUserId(userId);
    }
}