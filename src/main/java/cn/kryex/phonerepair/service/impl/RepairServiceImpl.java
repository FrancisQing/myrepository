package cn.kryex.phonerepair.service.impl;

import cn.kryex.phonerepair.entity.po.Repair;
import cn.kryex.phonerepair.entity.po.User;
import cn.kryex.phonerepair.entity.common.Result;
import cn.kryex.phonerepair.entity.dto.ReceptionistVO;
import cn.kryex.phonerepair.entity.dto.RepairQueryModule;
import cn.kryex.phonerepair.mapper.RepairMapper;
import cn.kryex.phonerepair.mapper.UserMapper;
import cn.kryex.phonerepair.service.RepairService;
import cn.kryex.phonerepair.utils.Md5Password;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RepairServiceImpl extends ServiceImpl<RepairMapper, Repair> implements RepairService {

    private final RepairMapper repairMapper;
    private final UserMapper userMapper;

    public RepairServiceImpl(RepairMapper repairMapper, UserMapper userMapper) {
        this.repairMapper = repairMapper;
        this.userMapper = userMapper;
    }

    @Override
    public Result<Map<String, Object>> getRepairListByCondition(RepairQueryModule repairQueryModule) {
        // 处理分页参数
        int pageNum = repairQueryModule.getPageNum()== null ? 1 : repairQueryModule.getPageNum();
        int pageSize = repairQueryModule.getPageSize()== null ? 10 : repairQueryModule.getPageSize();

        // 处理关键词和排序(保持不变)
        String searchKeyword = repairQueryModule.getSearchKeyword() == null ? "" : repairQueryModule.getSearchKeyword().trim();
        String sortField = repairQueryModule.getSortField() == null ? "createdAt" : repairQueryModule.getSortField().trim();
        String sortOrder = repairQueryModule.getSortOrder() == null ? "desc" : repairQueryModule.getSortOrder().trim();

        //使用 MyBatis-Plus 的 Page 对象(关键修改)
        Page<Repair> page = new Page<>(pageNum,pageSize);
        // 调用 mapper 方法
        IPage<Repair> PageResult = repairMapper.selectRepairByCondition(
                page,
                repairQueryModule.getUserId(),
                searchKeyword,
                sortField,
                sortOrder
        );
        Map<String, Object> map = new HashMap<>();
        map.put("count", PageResult.getTotal());
        map.put("repairRequest", PageResult.getRecords());
        return Result.success(map);
    }

    @Override
    public Result<List<ReceptionistVO>> getAllReceptionist() {
        // 查询所有接待人员（role_id=2）
        List<ReceptionistVO> receptionists = userMapper.selectList(
                new QueryWrapper<User>()
                        .select("user_id", "user_name")
                        .eq("role_id", 3)
        ).stream().map(user -> {
            ReceptionistVO vo = new ReceptionistVO();
            vo.setUserId(user.getUserId());
            vo.setUserName(user.getUserName());

            return vo;
        }).toList();
        return Result.success(receptionists);
    }

    @Override
    public boolean createRepair(Repair repair) {
        // 非空校验
        if(repair.getUserId() == null
                || repair.getPhoneModel() == null
                || repair.getPhoneIssueDescription() == null) {
            throw new RuntimeException("参数不能为空");
        }
        repair.setRequestStatus(1);
        repair.setCreatedAt(LocalDateTime.now());
        repair.setUpdatedAt(LocalDateTime.now());
        return this.save(repair);
    }

    @Override
    public boolean deleteRepair(Integer repairId, Integer userId, String password) {
        // 非空校验
        if(repairId == null || userId == null || password == null) {
            throw new RuntimeException("参数不能为空");
        }
        // 验证用户身份
        User user = userMapper.selectByUserId(userId);
        if(user == null || !Md5Password.generateMD5(password).equals(user.getUserPasswordHash())) {
            throw new RuntimeException("用户验证失败");
        }
        // 删除报修单
        return repairMapper.deleteRepairByIdAndUserId(repairId, userId) > 0;
    }
}
