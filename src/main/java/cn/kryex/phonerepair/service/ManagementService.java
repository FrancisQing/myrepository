package cn.kryex.phonerepair.service;

import cn.kryex.phonerepair.entity.Management;
import cn.kryex.phonerepair.entity.dto.ManagementQueryModule;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

public interface ManagementService extends IService<Management> {
    Map<String,Object> getAllManagement(ManagementQueryModule managementQueryModule);

    boolean deleteManagement(Integer repairId, Integer userId, String userPasswd);
}
