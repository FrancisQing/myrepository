package cn.kryex.phonerepair.entity.dto;

import lombok.Data;

@Data
public class RegisterUser {
    private String userName;
    private String userEmail;
    private String userPasswordHash;
}
