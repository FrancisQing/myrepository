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
@TableName(value = "yjx_user")              // 指定数据库表名
@Builder                                    // 构造器
public class User implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer userId;                 // 用户id

    @TableField(value = "user_name")
    private String userName;                // 用户名

    @TableField(value = "user_email")
    private String userEmail;               // 用户邮箱

    @TableField(value = "user_password_hash")
    private String userPasswordHash;        // 用户密码哈希值

    @TableField(value = "role_id")
    private String roleId;                  // 角色id

    @TableField(value = "user_bio")
    private String userBio;                 // 用户简介

    @TableField(value = "user_phone")
    private String userPhone;               // 用户电话

    @TableField(value = "user_gender")
    private String userGender;              // 用户性别

    @TableField(value = "user_last_active")
    private LocalDateTime userLastActive;   // 用户最后活跃时间

    @TableField(value = "user_created_at")
    private LocalDateTime userCreatedAt;    // 用户创建时间

    @TableField(value = "user_status")
    private String userStatus;              // 用户状态
}
