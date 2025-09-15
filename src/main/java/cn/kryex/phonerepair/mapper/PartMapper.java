package cn.kryex.phonerepair.mapper;

import cn.kryex.phonerepair.entity.Parts;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Select;

public interface PartMapper extends BaseMapper<Parts> {

    @Select("""
    SELECT 
        ya.*
    FROM yjx_parts ya
    WHERE 1=1
    -- 权限过滤：与你之前的逻辑一致（userId=1/5/8查全部，其他查自己关联的）
    AND ( #{userId} IN (1,5,8) )
    -- 搜索关键词：模糊匹配手机型号、维修描述、用户名（前端可能按这些字段搜索）
    AND ( 
        ya.part_name LIKE CONCAT('%', #{searchKeyword}, '%') 
        OR ya.part_description LIKE CONCAT('%', #{searchKeyword}, '%') 
    )
    -- 排序：与你之前的逻辑一致（支持按创建时间、维修ID排序）
    ORDER BY 
        CASE WHEN #{sortField} IS NOT NULL AND #{sortOrder} IS NOT NULL 
             THEN CASE #{sortField} 
                  WHEN 'createdAt' THEN ya.created_at 
                  WHEN 'part_id' THEN ya.part_id 
                  ELSE ya.created_at END 
        ELSE ya.created_at END 
        ${sortOrder == 'desc' ? 'DESC' : 'ASC'}
""")
    IPage<Parts> list(Page<Parts> page, Integer userId, String searchKeyword,
                      String sortField, String sortOrder);
}
