package cn.kryex.phonerepair.controller;

import cn.kryex.phonerepair.entity.common.Result;
import cn.kryex.phonerepair.entity.dto.ManagementQueryModule;
import cn.kryex.phonerepair.service.ManagementService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
