package cn.kryex.phonerepair.service.impl;

import cn.kryex.phonerepair.entity.Parts;
import cn.kryex.phonerepair.entity.dto.PartQueryModule;
import cn.kryex.phonerepair.mapper.PartMapper;
import cn.kryex.phonerepair.service.PartService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PartServiceImpl extends ServiceImpl<PartMapper, Parts> implements PartService {

    @Override
    public Map<String, Object> list(PartQueryModule partQueryModule) {
        int userId = partQueryModule.getUserId();
        String searchKeyword = partQueryModule.getSearchKeyword() == null ? "" : partQueryModule.getSearchKeyword().trim();
        int pageNum = partQueryModule.getPageNum() == null ? 1 : partQueryModule.getPageNum();
        int pageSize = partQueryModule.getPageSize() == null ? 10 : partQueryModule.getPageSize();
        String sortField = partQueryModule.getSortField() == null ? "createdAt" : partQueryModule.getSortField().trim();
        String sortOrder = partQueryModule.getSortOrder() == null ? "desc" : partQueryModule.getSortOrder().trim();

        Page<Parts> page = new Page<>(pageNum, pageSize);
        Map<String, Object> map = new HashMap<>();

        IPage<Parts> pageResult = baseMapper.list(page, userId, searchKeyword, sortField, sortOrder);
        map.put("pageResult", pageResult.getRecords());
        map.put("count", pageResult.getTotal());
        return map;
    }
}
