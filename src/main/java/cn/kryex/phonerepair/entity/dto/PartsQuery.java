package cn.kryex.phonerepair.entity.dto;

import lombok.Data;

@Data
public class PartsQuery {
    private Integer userId;
    private String searchKeyword;
    private Integer pageNum = 1;  // 设置默认值
    private Integer pageSize = 10;  // 设置默认值
    private String sortField;
    private String sortOrder;
}