package cn.kryex.phonerepair.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor                         // 全参构造函数
@NoArgsConstructor                          // 无参构造函数
@TableName(value = "yjx_parts")             // 指定数据库表名
@Builder                                    // 构造器
public class Parts implements Serializable {
    @TableId(type = IdType.AUTO)            //配件id
    private Integer partId;

    @TableField(value = "part_name")        //配件名称
    private String partName;

    @TableField(value = "part_description") //配件描述
    private String partDescription;

    @TableField(value = "part_price")       //配件价格
    private Double partPrice;

    @TableField(value = "stock_quantity")   //配件库存数量
    private Integer stockQuantity;

    @TableField(value = "supplier_id")      //配件供应商id
    private Integer supplierId;

    @TableField(value = "created_at")       //创建时间
    private LocalDateTime createdAt;

    @TableField(value = "updated_at")       //更新时间
    private LocalDateTime updatedAt;
}
