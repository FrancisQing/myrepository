package cn.kryex.phonerepair.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor                         // 全参构造函数
@NoArgsConstructor                          // 无参构造函数
@Builder                                    // 构造器
public class UserVO {
    private Integer userId;                 // 用户id
    private String userName;                // 用户名
    private String userEmail;               // 用户邮箱
    private String roleId;                  // 角色id
    private String userBio;                 // 用户简介
    private String userPhone;               // 用户电话
    private String userGender;              // 用户性别
    private LocalDateTime userLastActive;   // 用户最后活跃时间
    private LocalDateTime userCreatedAt;    // 用户创建时间
    private String userStatus;              // 用户状态
}
