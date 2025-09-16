package cn.kryex.phonerepair.mapper;

import cn.kryex.phonerepair.entity.po.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper extends BaseMapper<User> {

    @Select("""
    SELECT * FROM yjx_user WHERE user_id = #{userId}
""")
    User selectByUserId(Integer userId);

    IPage<User> list(IPage<User> page,
                     @Param("userId") Integer userId,
                     @Param("searchKeyword") String searchKeyword,
                     @Param("orderBy") String orderBy);

    int updateById(User user);
}