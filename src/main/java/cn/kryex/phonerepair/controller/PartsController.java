package cn.kryex.phonerepair.controller;

import cn.kryex.phonerepair.entity.common.PageResult;
import cn.kryex.phonerepair.entity.common.Result;
import cn.kryex.phonerepair.entity.dto.PartsDTO;
import cn.kryex.phonerepair.entity.dto.PartsQuery;
import cn.kryex.phonerepair.entity.vo.PartsVO;
import cn.kryex.phonerepair.service.PartsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/parts")
public class PartsController {

    private final PartsService partService;

    public PartsController(PartsService partService) {
        this.partService = partService;
    }

    @RequestMapping("/list")
    public Result<PageResult<PartsVO>> list(PartsQuery partsQuery) {
        PageResult<PartsVO> result = partService.list(partsQuery);
        return Result.success(result);
    }

    @RequestMapping("/add")
    public Result<String> addParts(@RequestBody PartsDTO partsDTO) {
        partService.addParts(partsDTO);
        return Result.success(null);
    }

    @RequestMapping("/delete/{partId}")
    public Result<String> deleteParts(@PathVariable Integer partId) {
        partService.removePartsById(partId);
        return Result.success(null);
    }

    @RequestMapping("/update")
    public Result<String> updateParts(@RequestBody PartsDTO partsDTO) {
        partService.updatePartsById(partsDTO);
        return Result.success(null);
    }

}
