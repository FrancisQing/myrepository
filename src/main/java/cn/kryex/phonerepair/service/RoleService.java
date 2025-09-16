package cn.kryex.phonerepair.service;

import cn.kryex.phonerepair.entity.po.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface RoleService extends IService<Role> {
    List<Role> listRole();
}
