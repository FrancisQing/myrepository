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
public class PartsVO {
    private Integer partId;
    private String partName;
    private String partDescription;
    private Double partPrice;
    private Integer stockQuantity;
    private Integer supplierId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
