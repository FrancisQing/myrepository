package cn.kryex.phonerepair.service.impl;

import cn.kryex.phonerepair.entity.Management;
import cn.kryex.phonerepair.entity.Repair;
import cn.kryex.phonerepair.entity.User;
import cn.kryex.phonerepair.entity.dto.ManagementCreateDTO;
import cn.kryex.phonerepair.entity.dto.ManagementQueryModule;
import cn.kryex.phonerepair.mapper.ManagementMapper;
import cn.kryex.phonerepair.mapper.RepairMapper;
import cn.kryex.phonerepair.mapper.UserMapper;
import cn.kryex.phonerepair.service.ManagementService;
import cn.kryex.phonerepair.utils.Md5Password;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class ManagementServiceImpl extends ServiceImpl<ManagementMapper, Management> implements ManagementService {
    @Autowired
    private ManagementMapper managementMapper;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RepairMapper repairMapper;

    @Override
    public Map<String, Object> getAllManagement(ManagementQueryModule managementQueryModule) {
        // 处理分页参数
        int pageNum = managementQueryModule.getPageNum() == null ? 1 : managementQueryModule.getPageNum();
        int pageSize = managementQueryModule.getPageSize() == null ? 10 : managementQueryModule.getPageSize();

        // 处理关键词和排序(保持不变)
        String searchKeyword = managementQueryModule.getSearchKeyword() == null ? "" : managementQueryModule.getSearchKeyword().trim();
        String sortField = managementQueryModule.getSortField() == null ? "createdAt" : managementQueryModule.getSortField().trim();
        String sortOrder = managementQueryModule.getSortOrder() == null ? "desc" : managementQueryModule.getSortOrder().trim();

        Page<Management> page = new Page<>(pageNum, pageSize);
        IPage<Management> pageResult = managementMapper.selectAllRepairManagement(
                page,
                managementQueryModule.getUserId(),
                searchKeyword,
                sortField,
                sortOrder
        );
        return Map.of(
                "count", pageResult.getTotal(),
                "repairManagementList", pageResult.getRecords()
        );
    }

    @Override
    public boolean createRepairManagement(ManagementCreateDTO managementCreateDTO) {
        // 非空校验
        if (managementCreateDTO.getRepairRequestId() == null
                || managementCreateDTO.getRepairNotes() == null
                || managementCreateDTO.getTechnicianId() == null) {
            throw new RuntimeException("用户ID和维修ID不能为空");
        }
        // 检查是否存在重复记录
        Repair repair = repairMapper.selectById(managementCreateDTO.getRepairRequestId());
        if (repair == null) {
            throw new RuntimeException("维修请求不存在");
        }
        // 创建新的管理记录
        Management management = new Management();
        management.setRepairRequestId(managementCreateDTO.getRepairRequestId());
        management.setTechnicianId(managementCreateDTO.getTechnicianId());
        management.setRepairNotes(managementCreateDTO.getRepairNotes());
        management.setRepairPrice("100");
        management.setPaymentStatus("待支付");
        management.setCreatedAt(LocalDateTime.now());
        management.setUpdatedAt(LocalDateTime.now());
        return this.save(management);
    }

    @Override
    public boolean deleteManagement(Integer repairId, Integer userId, String userPasswd) {
        // 非空校验
        if (repairId == null || userId == null || userPasswd == null || userPasswd.trim().isEmpty()) {
            throw new RuntimeException("参数不能为空");
        }
//        // 验证用户身份
//        User user = userMapper.selectByUserId(userId);
//        if(user == null || !Md5Password.generateMD5(userPasswd).equals(user.getUserPasswordHash())) {
//            throw new RuntimeException("用户验证失败");
//        }
        // 执行删除操作
        return managementMapper.deleteManagementByIdAndUserId(repairId, userId) > 0;
    }
}
