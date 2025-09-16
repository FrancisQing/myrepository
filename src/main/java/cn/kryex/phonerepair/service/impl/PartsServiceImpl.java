package cn.kryex.phonerepair.service.impl;

import cn.kryex.phonerepair.entity.common.PageResult;
import cn.kryex.phonerepair.entity.dto.PartsDTO;
import cn.kryex.phonerepair.entity.po.Parts;
import cn.kryex.phonerepair.entity.dto.PartsQuery;
import cn.kryex.phonerepair.entity.vo.PartsVO;
import cn.kryex.phonerepair.mapper.PartsMapper;
import cn.kryex.phonerepair.service.PartsService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class PartsServiceImpl extends ServiceImpl<PartsMapper, Parts> implements PartsService {

    private final PartsMapper partsMapper;

    private static final Set<String> SORT_FIELDS = Set.of("part_id", "created_at");
    private static final Set<String> SORT_ORDERS = Set.of("asc", "desc");

    public PartsServiceImpl(PartsMapper partsMapper) {
        this.partsMapper = partsMapper;
    }

    @Override
    public PageResult<PartsVO> list(PartsQuery partsQuery) {
        // 解析参数
        int userId = partsQuery.getUserId();
        String searchKeyword = partsQuery.getSearchKeyword() == null ? "" : partsQuery.getSearchKeyword().trim();
        int pageNum = partsQuery.getPageNum() == null ? 1 : partsQuery.getPageNum();
        int pageSize = partsQuery.getPageSize() == null ? 10 : partsQuery.getPageSize();

        // 字段白名单及排序方向白名单校验
        String safeField = Optional.ofNullable(partsQuery.getSortField())
                .filter(SORT_FIELDS::contains)
                .orElse("created_at");

        String safeOrder = Optional.ofNullable(partsQuery.getSortOrder())
                .map(String::toLowerCase)
                .filter(SORT_ORDERS::contains)
                .orElse("asc");

        String safeOrderBy = safeField + " " + safeOrder;

        // 分页查询
        Page<Parts> page = new Page<>(pageNum, pageSize);
        IPage<Parts> pageResult = partsMapper.list(page, userId, searchKeyword, safeOrderBy);

        // 转换为 PartsVo
        IPage<PartsVO> pageResultVo = pageResult.convert(item -> {
                PartsVO vo = new PartsVO();
                BeanUtils.copyProperties(item, vo);
                return vo;
        });

        // 构造分页结果并返回
        return new PageResult<>(pageResultVo);
    }

    @Override
    public void addParts(PartsDTO partsDTO) {
        // 参数校验
        if (partsDTO.getPartName() == null) {
            throw new IllegalArgumentException("配件名称不能为空");
        }
        if (partsDTO.getPartPrice() == null || partsDTO.getPartPrice() < 0) {
            throw new IllegalArgumentException("配件价格不能为空且必须大于等于0");
        }
        if (partsDTO.getStockQuantity() == null || partsDTO.getStockQuantity() < 0) {
            throw new IllegalArgumentException("库存不能为空且必须大于等于0");
        }
        if (partsDTO.getSupplierId() == null) {
            throw new IllegalArgumentException("供应商ID不能为空");
        }

        // 创建 Parts 实体并复制属性
        Parts parts = new Parts();
        BeanUtils.copyProperties(partsDTO, parts);
        parts.setPartDescription("测试");

        // 保存到数据库
        if (partsMapper.insert(parts) != 1) {
            throw new RuntimeException("添加配件失败");
        }
    }

    @Override
    public void updatePartsById(PartsDTO partsDTO) {
        // 参数校验
        if (partsDTO.getPartId() == null || partsDTO.getPartId() <= 0) {
            throw new IllegalArgumentException("无效的配件ID");
        }
        if (partsDTO.getPartName() == null) {
            throw new IllegalArgumentException("配件名称不能为空");
        }
        if (partsDTO.getPartPrice() == null || partsDTO.getPartPrice() < 0) {
            throw new IllegalArgumentException("配件价格不能为空且必须大于等于0");
        }
        if (partsDTO.getStockQuantity() == null || partsDTO.getStockQuantity() < 0) {
            throw new IllegalArgumentException("库存不能为空且必须大于等于0");
        }
        if (partsDTO.getSupplierId() == null) {
            throw new IllegalArgumentException("供应商ID不能为空");
        }

        // 创建 Parts 实体并复制属性
        Parts parts = new Parts();
        BeanUtils.copyProperties(partsDTO, parts);

        // 更新数据库记录
        if (partsMapper.updateById(parts) != 1) {
            throw new RuntimeException("更新配件失败，配件ID可能不存在");
        }
    }

    @Override
    public void removePartsById(Integer partId) {
        // 参数校验
        if (partId == null || partId <= 0) {
            throw new IllegalArgumentException("无效的配件ID");
        }
        // 删除配件
        if (partsMapper.deleteById(partId) != 1) {
            throw new RuntimeException("删除配件失败，配件ID可能不存在");
        }
    }
}
