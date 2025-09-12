package cn.kryex.phonerepair.service;

import cn.kryex.phonerepair.entity.Management;
import cn.kryex.phonerepair.entity.dto.ManagementCreateDTO;
import cn.kryex.phonerepair.entity.dto.ManagementQueryModule;
import cn.kryex.phonerepair.entity.dto.ManagementUpdateDTO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

public interface ManagementService extends IService<Management> {
    Map<String,Object> getAllManagement(ManagementQueryModule managementQueryModule);

    boolean createRepairManagement(ManagementCreateDTO managementCreateDTO);

    boolean updateRepairManagement(ManagementUpdateDTO managementUpdateDTO);

    boolean deleteManagement(Integer repairId, Integer userId, String userPasswd);
}
