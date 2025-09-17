package cn.kryex.phonerepair.controller;


import cn.kryex.phonerepair.entity.common.Result;
import cn.kryex.phonerepair.entity.po.Supplier;
import cn.kryex.phonerepair.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/supplier")
public class SupplierManagementController {

    @Autowired
    private SupplierService supplierService;

    @RequestMapping("/getAllSupplierManagement")
    public Result<Map<String, Object>> getAllSuppliersManagement(@RequestParam(required = false) String searchKeyword,
                                                                 @RequestParam(defaultValue = "1") Integer pageNum,
                                                                 @RequestParam(defaultValue = "10") Integer pageSize,
                                                                 @RequestParam(defaultValue = "created_at") String sortField,
                                                                 @RequestParam(defaultValue = "desc") String sortOrder) {
        try {
            //参数合法性校验，排序字段限制
            if (!"supplier _management_id".equals(sortField) &&
                    !"supplier_id".equals(sortField) &&
                    !"part id".equals(sortField)&&
                    !"created_at".equals(sortField)) {
                return Result.fail("非法的排序字段", 400);
            }
            Map<String, Object> result = supplierService.getAllSuppliersManagement(searchKeyword, pageNum, pageSize, sortField, sortOrder);
            return Result.success(result);
        } catch (Exception e) {
            return Result.fail("服务器异常，请稍后再试",500);
        }
    }

    @RequestMapping("/createSupplierManagement")
    public Result<String> createSupplierManagement(@RequestBody Supplier supplier) {
        Boolean result = supplierService.createSupplierManagement(supplier);
        if (result) {
            return Result.success(null);
        } else {
            return Result.fail("创建失败", 400);
        }
    }

    @RequestMapping("/updateSupplierManagement")
    public Result<String> updateSupplierManagement(@RequestBody Supplier supplier) {
        Boolean result = supplierService.updateSupplierManagement(supplier);
        if (result) {
            return Result.success(null);
        } else {
            return Result.fail("更新失败", 400);
        }
    }

    @RequestMapping("/deleteSupplierManagement")
    public Result<String> deleteSupplierManagement(@RequestParam Integer supplierManagementId,
                                                   @RequestParam Integer userId,
                                                   @RequestParam String userPasswd) {
        Boolean result = supplierService.deleteSupplierManagement(supplierManagementId, userId, userPasswd);
        if (result) {
            return Result.success(null);
        } else {
            return Result.fail("删除失败", 400);
        }
    }
}
