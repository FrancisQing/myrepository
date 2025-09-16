package cn.kryex.phonerepair.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
//import com.github.pagehelper.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page; // 使用MP的Page
import cn.kryex.phonerepair.entity.po.Repair;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface RepairMapper extends BaseMapper<Repair> {
    /**
     * 分页查询维修单：支持权限过滤（userId=1/3查全部，其他查自己）、搜索、排序
     * @param page 分页参数（页码、每页条数）
     * @param userId 当前登录用户ID（用于权限过滤）
     * @param searchKeyword 搜索关键词（模糊查手机型号、问题描述）
     * @param sortField 排序字段（如createdAt、requestId）
     * @param sortOrder 排序方向（asc/desc）
     * @return 分页结果（含数据列表和总条数）
     */
    @Select("""
    SELECT 
        rr.*,  -- 保留维修单表所有字段
        u.user_name AS receptionistName  -- 从用户表获取接待人员姓名，别名对应Repair类的receptionistName字段
    FROM yjx_repair_request rr
    -- 关键：关联用户表，通过维修单的receptionistId匹配用户表的userId
    LEFT JOIN yjx_user u ON rr.receptionist_id = u.user_id
    -- 修改：使用不同的别名避免关键字冲突
    LEFT JOIN yjx_user cu ON cu.user_id = #{userId}
    WHERE 1=1
    -- 修改后的权限过滤：role_id为特定值(如1或3)查全部，其他只查自己创建的
    AND ( cu.role_id IN (1,3) OR rr.user_id = #{userId} )
    -- 搜索关键词：模糊匹配手机型号或问题描述
    AND ( rr.phone_model LIKE CONCAT('%', #{searchKeyword}, '%') 
          OR rr.phone_issue_description LIKE CONCAT('%', #{searchKeyword}, '%') )
    -- 排序：有排序字段则按字段排序，无则默认按创建时间降序
    ORDER BY 
        CASE WHEN #{sortField} IS NOT NULL AND #{sortOrder} IS NOT NULL 
             THEN CASE #{sortField} 
                  WHEN 'createdAt' THEN rr.created_at 
                  WHEN 'requestId' THEN rr.request_id 
                  ELSE rr.created_at END 
        ELSE rr.created_at END 
        ${sortOrder == 'desc' ? 'DESC' : 'ASC'}
""")
    IPage<Repair> selectRepairByCondition( Page<Repair> page,                // MP分页对象（自动处理limit和offset）
                                           @Param("userId") Integer userId,            // 权限过滤用的用户ID
                                           @Param("searchKeyword") String searchKeyword, // 搜索关键词
                                           @Param("sortField") String sortField,       // 排序字段
                                           @Param("sortOrder") String sortOrder );       // 排序方向);

    @Select("SELECT * FROM yjx_repair_request WHERE request_id = #{repairId}")
    Repair selectById(@Param("repairId") Integer repairId);

    @Delete("""
    DELETE FROM yjx_repair_request
    WHERE request_id = #{repairId} 
      AND ( user_id = #{userId} or 
            user_id in (1,3) )  -- 管理员或接待员可以删除任何单，其他用户只能删除自己的单
""")
    int deleteRepairByIdAndUserId(@Param("repairId") Integer repairId, @Param("userId") Integer userId);
}