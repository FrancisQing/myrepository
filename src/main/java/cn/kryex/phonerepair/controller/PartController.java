package cn.kryex.phonerepair.controller;

import cn.kryex.phonerepair.entity.common.Result;
import cn.kryex.phonerepair.entity.dto.PartQueryModule;
import cn.kryex.phonerepair.service.PartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/parts")
public class PartController {

    @Autowired
    private PartService partService;

    @RequestMapping("/list")
    public Result<Map<String, Object>> list(PartQueryModule partQueryModule) {
        Map<String, Object> map = partService.list(partQueryModule);
        return Result.success(map);
    }
}
