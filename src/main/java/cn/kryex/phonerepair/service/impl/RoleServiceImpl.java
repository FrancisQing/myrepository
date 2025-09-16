package cn.kryex.phonerepair.service.impl;

import cn.kryex.phonerepair.entity.po.Role;
import cn.kryex.phonerepair.mapper.RoleMapper;
import cn.kryex.phonerepair.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    private final RoleMapper roleMapper;

    public RoleServiceImpl(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Override
    public List<Role> listRole() {
        return roleMapper.list();
    }
}
