package cn.kryex.phonerepair.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor                         // 全参构造函数
@NoArgsConstructor                          // 无参构造函数
@TableName(value = "yjx_role")              // 指定数据库表名
@Builder                                    // 构造器
public class Role {
    @TableId(type = IdType.AUTO)
    private Integer roleId;                 // 角色id

    @TableField(value = "role_name")
    private String roleName;                // 角色名称

    @TableField(value = "role_description")
    private String roleDescription;         // 角色描述
}