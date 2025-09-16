package cn.kryex.phonerepair.service;

import cn.kryex.phonerepair.entity.po.Repair;
import cn.kryex.phonerepair.entity.common.Result;
import cn.kryex.phonerepair.entity.dto.ReceptionistVO;
import cn.kryex.phonerepair.entity.dto.RepairQueryModule;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

public interface RepairService extends IService<Repair> {
    Result<Map<String, Object>> getRepairListByCondition(RepairQueryModule repairQueryModule);

    Result<List<ReceptionistVO>> getAllReceptionist();

    boolean createRepair(Repair repair);
    boolean deleteRepair(Integer repairId, Integer userId, String password);
}
