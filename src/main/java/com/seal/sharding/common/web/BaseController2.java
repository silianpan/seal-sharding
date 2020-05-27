package com.seal.sharding.common.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.seal.sharding.common.kit.LegaSqlHelper;
import com.seal.sharding.common.page.LegaPage;
import com.seal.sharding.common.query.QueryCondition;
import com.seal.sharding.common.query.QueryItem;
import com.seal.sharding.common.ret.Rb;
import com.seal.sharding.common.service.BaseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * @author: liupan
 * @date: 2018/7/17 10:05
 * @description
 */
public class BaseController2<Bs extends BaseService, Entity> {

    @Autowired
    protected Bs baseService;

    protected Class<Entity> getEntityClass() {
        return (Class<Entity>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

    /**
     * @param
     * @return com.seal.sharding.ret.RetBack
     * @description 获取所有的数据
     * @author: liupan
     * @date: 2018-08-10
     */
    @PostMapping("/all")
    @ApiOperation(value = "根据条件获取满足条件的数据", notes = "注意参数")
    public Rb<List<Entity>> findAll(@RequestBody QueryCondition condition) throws IllegalAccessException, InstantiationException {
        List<Entity> results = baseService.list(LegaSqlHelper.fillWrapper(condition, new QueryWrapper<>(getEntityClass().newInstance())));
        return Rb.ok(results);
    }

    /**
     * @param entity
     * @return com.seal.sharding.ret.RetBack
     * @description 新增 or 修改
     * @author: liupan
     * @date: 2018-08-10
     */
    @PostMapping("/save")
    @ApiOperation(value = "insert or update ", notes = "注意参数")
    public Rb<Entity> save(@RequestBody Entity entity) {
        baseService.saveOrUpdate(entity);
        return Rb.ok(entity, "保存成功");
//        if (entity != null && entity.getId() != null) {
//            // update
//            baseService.updateById(entity);
//            return Rb.ok(entity, "修改成功");
//        } else {
//            // insert
//            baseService.save(entity);
//            return Rb.ok(entity, "新增成功");
//        }
    }

    /**
     * @param id
     * @return com.seal.sharding.ret.RetBack
     * @description 根据id 查询数据
     * @author: liupan
     * @date: 2018-08-10
     */
    @GetMapping("/findById")
    @ApiOperation(value = "根据id查找数据", notes = "注意参数")
    public Rb<Entity> findById(@RequestParam("id") Serializable id) {

        Entity entity = (Entity) baseService.getById(id);
        return Rb.ok(entity);
    }

    /**
     * @param page
     * @return com.seal.sharding.ret.RetBack
     * @description 分页查询
     * @author: liupan
     * @date: 2018-08-10
     */
    @PostMapping("/page")
    @ApiOperation(value = "根据条件分页查询数据", notes = "注意参数")
    public Rb<IPage> page(@RequestBody LegaPage page) throws IllegalAccessException, InstantiationException {
        IPage<Entity> entityPage = baseService.page(page, new QueryWrapper<>(getEntityClass().newInstance()));
        return Rb.ok(entityPage);
    }

    /**
     * @param id
     * @return com.seal.sharding.ret.RetBack
     * @description 物理删除
     * @author: liupan
     * @date: 2018-08-10
     */
    @DeleteMapping("/delete")
    @ApiOperation(value = "根据id物理删除数据", notes = "注意参数")
    public Rb<Boolean> delete(@RequestParam("id") Serializable id) {
        boolean isSuccess = baseService.delPhysicalById(id);
        return Rb.ok(isSuccess);
    }

    /**
     * @param id
     * @return com.seal.sharding.ret.RetBack
     * @description 逻辑删除
     * @author: liupan
     * @date: 2018/7/31 15:50
     */
    @DeleteMapping("/logic/delete")
    @ApiOperation(value = "根据id逻辑删除数据", notes = "注意参数")
    public Rb<Boolean> logicDelete(@RequestParam("id") Serializable id) {
        boolean isSuccess = baseService.removeById(id);
        return Rb.ok(isSuccess);
    }

    /**
     * 根据条件筛选家庭数量
     *
     * @param queryItems
     * @return com.seal.sharding.common.ret.RetBack
     * @description
     * @author: liupan
     * @date: 2018/10/22 14:51
     */
    @PostMapping("/count/number")
    @ApiOperation(value = "根据条件筛选数量")
    public Rb<Integer> countByCondition(@RequestBody List<QueryItem> queryItems) {
        QueryWrapper queryWrapper = LegaSqlHelper.fillWrapperWithConditions(queryItems, null);
        return Rb.ok(baseService.count(queryWrapper));
    }
}
