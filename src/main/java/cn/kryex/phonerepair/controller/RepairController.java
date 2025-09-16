package cn.kryex.phonerepair.controller;

import cn.kryex.phonerepair.entity.po.Repair;
import cn.kryex.phonerepair.entity.common.Result;
import cn.kryex.phonerepair.entity.dto.ReceptionistVO;
import cn.kryex.phonerepair.entity.dto.RepairQueryModule;
import cn.kryex.phonerepair.service.RepairService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/repair")
public class RepairController {
    private final RepairService repairService;

    public RepairController(RepairService repairService) {
        this.repairService = repairService;
    }

    @RequestMapping("/getAllRepair")
    public Result<Map<String, Object>> getAllRepair(RepairQueryModule repairQueryModule) {
        return repairService.getRepairListByCondition(repairQueryModule);
    }

    @GetMapping("/getAllReceptionist")
    public Result<List<ReceptionistVO>> getAllReceptionist() {
        return repairService.getAllReceptionist();
    }

    @PostMapping("/createRepair")
    public Result<String> createRepair(@RequestBody Repair repair) {
        boolean saveResult = repairService.createRepair(repair);
        if (saveResult) {
            return Result.success("Repair request created successfully");
        } else {
            return Result.fail("Failed to create repair request",500);
        }
    }

    @PostMapping("/deleteRepair")
    public Result<String> deleteRepair(@RequestParam("repairId") Integer repairId,
                                       @RequestParam("userId") Integer userId,
                                       @RequestParam("password") String password){
        boolean deleteResult = repairService.deleteRepair(repairId, userId, password);
        if(deleteResult){
            return Result.success("Repair request deleted successfully");
        } else {
            return Result.fail("Failed to delete repair request", 500);
        }
    }
}
