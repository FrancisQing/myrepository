package cn.kryex.phonerepair.mapper;

import cn.kryex.phonerepair.entity.po.Supplier;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Map;

public interface SupplierMapper extends BaseMapper<Supplier> {
    @Select("""
        SELECT\s
            sm.*, -- 供应商管理表所有字段
            u.user_name AS supplierName, -- 从用户表获取供应商名称
            p.part_name AS partName -- 从配件表获取配件名称
        FROM yjx_supplier_management sm
        -- 关键：通过sm.supplier_id关联到用户表，并过滤出角色为供应商（role_id=5）的用户
        LEFT JOIN yjx_user u ON sm.supplier_id = u.user_id AND u.role_id = 5
        -- 关键：通过sm.part_id关联到配件表
        LEFT JOIN yjx_parts p ON sm.part_id = p.part_id
        WHERE 1=1
        -- 搜索关键词：模糊匹配供应商编号或配件编号
        AND ( CAST(sm.supplier_id AS CHAR) LIKE CONCAT('%', #{searchKeyword}, '%')
              OR CAST(sm.part_id AS CHAR) LIKE CONCAT('%', #{searchKeyword}, '%') )
        -- 排序：按指定字段排序，若无则默认按创建时间降序
        ORDER BY\s
            CASE WHEN #{sortField} IS NOT NULL AND #{sortOrder} IS NOT NULL\s
                 THEN CASE #{sortField}
                      WHEN 'supplier_management_id' THEN sm.supplier_management_id
                      WHEN 'supplier_id' THEN sm.supplier_id
                      WHEN 'part_id' THEN sm.part_id
                      WHEN 'created_at' THEN sm.created_at
                      ELSE sm.created_at END\s
            ELSE sm.created_at END\s
            ${sortOrder == 'desc' ? 'DESC' : 'ASC'}
""")
    IPage<Supplier> selectSupplierByCondition(IPage<Supplier> page, String searchKeyword, String sortField, String sortOrder);

    @Insert("""
    INSERT INTO yjx_supplier_management (supplier_id, part_id, supply_quantity, created_at, updated_at) 
    VALUES ( #{supplierId}, #{partId}, #{supplyQuantity}, #{createdAt}, #{updatedAt} )
""")
    int insert(Supplier supplier);

    @Update("""
    UPDATE yjx_supplier_management
    SET supplier_id = #{supplierId},
        part_id = #{partId},
        supply_quantity = #{supplyQuantity},
        updated_at = #{updatedAt}
    WHERE supplier_management_id = #{supplierManagementId}
""")
    int updateById(Supplier supplier);

    @Delete("""
    DELETE FROM yjx_supplier_management
    WHERE supplier_management_id = #{supplierManagementId}
""")
    int deleteByManagementId(Integer supplierManagementId);
}
