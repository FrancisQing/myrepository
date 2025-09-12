package cn.kryex.phonerepair.controller;

import cn.kryex.phonerepair.entity.common.Result;
import cn.kryex.phonerepair.entity.dto.ManagementCreateDTO;
import cn.kryex.phonerepair.entity.dto.ManagementQueryModule;
import cn.kryex.phonerepair.service.ManagementService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/management")
public class ManagementController {
    private final ManagementService managementService;

    public ManagementController(ManagementService managementService) {
        this.managementService = managementService;
    }

    @RequestMapping("/getAllRepairManagement")
    public Result<Map<String, Object>> getAllRepairManagement(ManagementQueryModule managementQueryModule) {
        Map<String, Object> result = managementService.getAllManagement(managementQueryModule);
        return Result.success(result);
    }
    @RequestMapping("/createRepairManagement")
    public Result<String> createRepairManagement(@RequestBody ManagementCreateDTO managementCreateDTO) {
        boolean createResult = managementService.createRepairManagement(managementCreateDTO);
        if (createResult) {
            return Result.success("Management record created successfully");
        } else {
            return Result.fail("Failed to create management record", 500);
        }
    }


                                                 @PostMapping("/deleteRepairManagement")
    public Result<String> deleteRepairManagement(@RequestParam ("repairId") Integer repairId,
                                                 @RequestParam ("userId") Integer userId,
                                                 @RequestParam ("userPasswd") String userPasswd){
        boolean deleteResult = managementService.deleteManagement(repairId, userId, userPasswd);
        if (deleteResult) {
            return Result.success("Management record deleted successfully");
        } else {
            return Result.fail("Failed to delete management record", 500);
        }
    }
}
