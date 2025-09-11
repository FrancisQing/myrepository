package cn.kryex.phonerepair.entity.dto;

import lombok.Data;

@Data
public class LoginUser {
    private Integer userId;
    private String userName;
    private String userEmail;
    private String roleId;
    private String userBio;
    private String userPhone;

}