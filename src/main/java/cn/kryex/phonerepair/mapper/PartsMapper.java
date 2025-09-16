package cn.kryex.phonerepair.mapper;

import cn.kryex.phonerepair.entity.po.Parts;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

public interface PartsMapper extends BaseMapper<Parts> {

    IPage<Parts> list(Page<Parts> page,
                      @Param("userId") Integer userId,
                      @Param("searchKeyword") String searchKeyword,
                      @Param("orderBy") String orderBy);

    int insert(Parts parts);
    int updateById(Parts parts);
    int deleteById(@Param("partId") Integer partId);
}
