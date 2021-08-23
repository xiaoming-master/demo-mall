package com.ming.mall.controller;


import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ming.mall.common.api.CommonPage;
import com.ming.mall.common.api.CommonResult;
import com.ming.mall.model.PmsBrand;
import com.ming.mall.service.IPmsAlbumPicService;
import com.ming.mall.service.IPmsBrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 品牌表 前端控制器
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
@Api(tags = "PmsBrandController", description = "商品品牌管理")
@RestController
@RequestMapping("/brand")
public class PmsBrandController {

    @Autowired
    private IPmsBrandService brandService;


    @ApiOperation(value = "通id查询商品品牌信息")
    @GetMapping("/{id}")
    public CommonResult getBrandById(@PathVariable Long id) {
        PmsBrand brand = brandService.getById(id);
        return CommonResult.success(brand);
    }

    @ApiOperation("添加品牌")
    @PostMapping("/create")
    public CommonResult addBrand(@RequestBody PmsBrand pmsBrand) {
        int count = brandService.addBrand(pmsBrand);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("通过id删除品牌")
    @PostMapping("/delete/{id}")
    public CommonResult deleteBrandById(@PathVariable Long id) {
        boolean flag = brandService.removeById(id);
        if (flag) {
            return CommonResult.success(flag);
        } else {
            return CommonResult.failed();
        }
    }


    @ApiOperation("批量删除品牌")
    @PostMapping("/delete/batch")
    public CommonResult deleteBatchByIds(@RequestParam List<Long> ids) {
        boolean flag = brandService.removeByIds(ids);
        if (flag) {
            return CommonResult.success(flag);
        } else {
            return CommonResult.failed();
        }
    }


    @ApiOperation("根据品牌名称分页模糊查询")
    @GetMapping("/list")
    public CommonResult getBrandByKeyword(@RequestParam(required = false) String keyword,
                                          @RequestParam(defaultValue = "1") Integer pageNum,
                                          @RequestParam(defaultValue = "5") Integer pageSize) {
        List<PmsBrand> brands = brandService.getBrandByKeyword(keyword, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(brands));
    }

    @ApiOperation(value = "获取所有的品牌")
    @GetMapping("/listAll")
    public CommonResult<List<PmsBrand>> getAllBrands() {
        List<PmsBrand> brands = brandService.list();
        return CommonResult.success(brands);
    }


    @ApiOperation("更新品牌信息")
    @PostMapping("/update/{id}")
    public CommonResult updateBrand(@PathVariable Long id, @RequestBody PmsBrand pmsBrandParam) {
        pmsBrandParam.setId(id);
        int count = brandService.updateBrand(id, pmsBrandParam);
        if (count > 0) {
            return CommonResult.success(count);
        }else {
            return CommonResult.failed();
        }
    }
}
