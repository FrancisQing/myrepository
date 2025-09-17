package cn.kryex.phonerepair.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor                              // 无参构造函数
@AllArgsConstructor                             // 全参构造函数
@Builder                                        // 构造器
@TableName("yjx_supplier_management")           // 指定数据库表名
public class Supplier {
    @TableId(type = IdType.AUTO)                // 主键，自动递增
    private Integer supplierManagementId;

    @TableField("supplier_id")                  // 供应商ID
    private Integer supplierId;

    @TableField("part_id")                      // 配件ID
    private Integer partId;

    @TableField("supply_quantity")              // 供应数量
    private Integer supplyQuantity;

    @TableField("create_at")                    // 创建时间
    private LocalDateTime createdAt;

    @TableField("update_at")                    // 更新时间
    private LocalDateTime updatedAt;
    // 以下为关联表字段（非数据库表字段，用于前端展示）

    @TableField(exist = false)
    private String supplierName; // 来自 yjx_supplier_management 表的供应商名称

    @TableField(exist = false)
    private String partName;     // 来自 yjx_parts 表的配件名称
}