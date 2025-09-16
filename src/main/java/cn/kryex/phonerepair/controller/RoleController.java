package cn.kryex.phonerepair.controller;

import cn.kryex.phonerepair.entity.common.Result;
import cn.kryex.phonerepair.entity.po.Role;
import cn.kryex.phonerepair.service.RoleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {
    private final cn.kryex.phonerepair.service.RoleService RoleService;

    public RoleController(RoleService RoleService) {
        this.RoleService = RoleService;
    }

    @RequestMapping("/list")
    public Result<List<Role>> list() {
        List<Role> result = RoleService.listRole();
        return Result.success(result);
    }
}
