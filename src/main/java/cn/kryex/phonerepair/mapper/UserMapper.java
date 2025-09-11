package cn.kryex.phonerepair.mapper;

import cn.kryex.phonerepair.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

public interface UserMapper extends BaseMapper<User> {

    @Select("""
    SELECT * FROM yjx_user WHERE user_id = #{userId}
""")
    User selectByUserId(Integer userId);
}