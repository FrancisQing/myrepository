package cn.kryex.phonerepair.service;

import cn.kryex.phonerepair.entity.common.PageResult;
import cn.kryex.phonerepair.entity.dto.PartsDTO;
import cn.kryex.phonerepair.entity.po.Parts;
import cn.kryex.phonerepair.entity.dto.PartsQuery;
import cn.kryex.phonerepair.entity.vo.PartsVO;
import com.baomidou.mybatisplus.extension.service.IService;

public interface PartsService extends IService<Parts> {
    PageResult<PartsVO> list(PartsQuery partsQuery);

    void addParts(PartsDTO partsDTO);
    void updatePartsById(PartsDTO partsDTO);
    void removePartsById(Integer partId);
}
