package cn.kryex.phonerepair.service;

import cn.kryex.phonerepair.entity.Parts;
import cn.kryex.phonerepair.entity.dto.PartQueryModule;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

public interface PartService extends IService<Parts> {

    Map<String,Object> list(PartQueryModule partQueryModule);

}
