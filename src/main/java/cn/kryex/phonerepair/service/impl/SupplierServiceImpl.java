package cn.kryex.phonerepair.service.impl;

import cn.kryex.phonerepair.entity.po.Supplier;
import cn.kryex.phonerepair.entity.po.User;
import cn.kryex.phonerepair.mapper.SupplierMapper;
import cn.kryex.phonerepair.mapper.UserMapper;
import cn.kryex.phonerepair.service.SupplierService;
import cn.kryex.phonerepair.utils.Md5Password;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class SupplierServiceImpl extends ServiceImpl<SupplierMapper, Supplier> implements SupplierService {

    @Autowired
    private SupplierMapper supplierMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public Map<String, Object> getAllSuppliersManagement(String searchKeyword, Integer pageNum, Integer pageSize, String sortField, String sortOrder) {
        Page<Supplier> page = new Page<>(pageNum, pageSize);
        IPage<Supplier> supplierIPage = supplierMapper.selectSupplierByCondition(page, searchKeyword, sortField, sortOrder);
        Map<String, Object> result = new HashMap<>();
        result.put("supplierManagementList", supplierIPage.getRecords());
        result.put("count", supplierIPage.getTotal());
        return result;
    }

    @Override
    public Boolean createSupplierManagement(Supplier supplier) {
        supplier.setSupplierManagementId(null);
        supplier.setCreatedAt(LocalDateTime.now());
        supplier.setUpdatedAt(LocalDateTime.now());
        return supplierMapper.insert(supplier) > 0;
    }

    @Override
    public Boolean updateSupplierManagement(Supplier supplier) {
        supplier.setUpdatedAt(LocalDateTime.now());
        return supplierMapper.updateById(supplier) > 0;
    }

    @Override
    public Boolean deleteSupplierManagement(Integer supplierManagementId, Integer userId, String userPassword) {
        User user = userMapper.selectByUserId(userId);
        if (user == null ) { // 仅管理员可删除
            throw new RuntimeException("用户不存在或无权限");
        }
        String password = user.getUserPasswordHash();
        if (password == null || !password.equals(Md5Password.generateMD5(userPassword))) {
            throw new RuntimeException("密码错误");
        }
        return supplierMapper.deleteByManagementId(supplierManagementId) > 0;
    }
}
