package com.imooc.controller;

import com.imooc.enums.YesOrNo;
import com.imooc.pojo.Carousel;
import com.imooc.pojo.Category;
import com.imooc.service.CarouselService;
import com.imooc.service.CategoryService;
import com.imooc.utils.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Api(value = "首页",tags = "首页展示的相关接口")
@RestController
@RequestMapping("index")
public class IndexController {

    @Autowired
    private CarouselService carouselService;

    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "获取首页轮播图列表",notes = "获取首页轮播图列表",httpMethod = "GET")
    @GetMapping("/carousel")
    public JsonResult carousel(){
        List<Carousel> list = carouselService.queryAll(YesOrNo.YES.type);

        return JsonResult.ok(list);
    }

    /**
     * 首页分类展示需求
     * 1.第一次刷新首页查询大分类，渲染到展示页面
     * 2.如果鼠标移动到大分类，则加载其子分类，如果已经存在子分类，则不需要加载 -》懒加载
     */

    @ApiOperation(value = "获取商品一级分类",notes = "获取商品一级分类",httpMethod = "GET")
    @GetMapping("/cats")
    public JsonResult cats(){
        List<Category> list = categoryService.queryAllRootLevelCat();

        return JsonResult.ok(list);
    }
}
