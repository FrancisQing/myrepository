package cn.kryex.phonerepair.service;

import cn.kryex.phonerepair.entity.po.Supplier;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

public interface SupplierService extends IService<Supplier> {

    Map<String, Object> getAllSuppliersManagement(String searchKeyword, Integer pageNum, Integer pageSize, String sortField, String sortOrder);

    Boolean createSupplierManagement(Supplier supplier);
    Boolean updateSupplierManagement(Supplier supplier);
    Boolean deleteSupplierManagement(Integer supplierManagementId, Integer userId, String userPassword);
}
