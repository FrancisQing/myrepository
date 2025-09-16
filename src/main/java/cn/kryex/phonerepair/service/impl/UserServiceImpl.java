package cn.kryex.phonerepair.service.impl;

import cn.kryex.phonerepair.entity.common.PageResult;
import cn.kryex.phonerepair.entity.dto.LoginUser;
import cn.kryex.phonerepair.entity.dto.UserDTO;
import cn.kryex.phonerepair.entity.dto.UserQuery;
import cn.kryex.phonerepair.entity.po.User;
import cn.kryex.phonerepair.entity.dto.RegisterUser;
import cn.kryex.phonerepair.entity.vo.UserVO;
import cn.kryex.phonerepair.mapper.UserMapper;
import cn.kryex.phonerepair.service.UserService;
import cn.kryex.phonerepair.utils.Md5Password;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final UserMapper userMapper;

    private static final Set<String> SORT_FIELDS = Set.of("user_name", "user_created_at", "user_last_active", "user_status");
    private static final Set<String> SORT_ORDERS = Set.of("asc", "desc");

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

    @Override
    public PageResult<UserVO> list(UserQuery userQuery) {
        // 解析参数
        int userId = userQuery.getUserId();
        String searchKeyword = userQuery.getSearchKeyword() == null ? "" : userQuery.getSearchKeyword().trim();
        int pageNum = userQuery.getPageNum() == null ? 1 : userQuery.getPageNum();
        int pageSize = userQuery.getPageSize() == null ? 10 : userQuery.getPageSize();

        // 字段白名单及排序方向白名单校验
        String safeField = Optional.ofNullable(userQuery.getSortField())
                .filter(SORT_FIELDS::contains)
                .orElse("user_created_at");

        String safeOrder = Optional.ofNullable(userQuery.getSortOrder())
                .map(String::toLowerCase)
                .filter(SORT_ORDERS::contains)
                .orElse("asc");

        String safeOrderBy = safeField + " " + safeOrder;

        // 分页查询
        Page<User> page = new Page<>(pageNum, pageSize);
        IPage<User> pageResult = userMapper.list(page, userId, searchKeyword, safeOrderBy);

        // 转换为 UserVo
        IPage<UserVO> pageResultVo = pageResult.convert(item -> {
            UserVO vo = new UserVO();
            BeanUtils.copyProperties(item, vo);
            return vo;
        });

        // 构造分页结果并返回
        return new PageResult<>(pageResultVo);
    }

    @Override
    public void updateUserById(UserDTO userDTO) {
        // 参数校验
        if (userDTO.getUserId() == null || userDTO.getUserId() <= 0) {
            throw new IllegalArgumentException("无效的用户ID");
        }
        if (userDTO.getUserName() == null) {
            throw new IllegalArgumentException("用户名不能为空");
        }
        if (userDTO.getUserEmail() == null) {
            throw new IllegalArgumentException("用户邮箱不能为空");
        }
        if (userDTO.getRoleId() == null) {
            throw new IllegalArgumentException("用户角色不能为空");
        }

        // 邮箱检验
        User user = this.getOne(new QueryWrapper<User>().eq("user_id", userDTO.getUserId()));
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        if (user.getUserEmail().equals(userDTO.getUserEmail())) {
            User tmp = this.getOne(new QueryWrapper<User>()
                    .eq("user_email", userDTO.getUserEmail())
                    .ne("user_id", userDTO.getUserId()));
            if (tmp != null) {
                throw new RuntimeException("用户邮箱已存在");
            }
        }

        // 创建 User 实体并复制属性
        User userUpdate = new User();
        BeanUtils.copyProperties(userDTO, userUpdate);

        // 更新到数据库
        if (userMapper.updateById(userUpdate) != 1) {
            throw new RuntimeException("更新用户失败");
        }
    }
}