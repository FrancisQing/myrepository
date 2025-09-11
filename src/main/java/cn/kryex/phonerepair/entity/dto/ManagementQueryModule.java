package cn.kryex.phonerepair.entity.dto;

import lombok.Data;

@Data
public class ManagementQueryModule {
    Integer userId;
    String userRole;
    String searchKeyword;
    Integer pageNum = 1;
    Integer pageSize = 10;
    String sortField;
    String sortOrder; // "ascend" or "descend"
}
