package com.wist.tensquare.spit.controller;

import com.wist.tensquare.spit.pojo.Spit;
import com.wist.tensquare.spit.service.SpitService;
import entity.StatusCode;
import entity.WebResult;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author simptx
 */
@RestController
@CrossOrigin
@RequestMapping("/spit")
public class SpitController {
    @Resource
    private SpitService spitService;
    @Resource
    private RedisTemplate<String, Integer> redisTemplate;

    @GetMapping
    public WebResult findAll() {
        return new WebResult(StatusCode.OK, true, "查询成功", spitService.findAll());
    }

    @GetMapping(value = "/{spitId}")
    public WebResult findById(@PathVariable String spitId) {
        return new WebResult(StatusCode.OK, true, "查询成功", spitService.findById(spitId));
    }

    @PostMapping
    public WebResult save(@RequestBody Spit spit) {
        spitService.save(spit);
        return new WebResult(StatusCode.OK, true, "保存成功");
    }

    @PutMapping(value = "/{spitId}")
    public WebResult update(@PathVariable String spitId, @RequestBody Spit spit) {
        spit.set_id(spitId);
        spitService.update(spit);
        return new WebResult(StatusCode.OK, true, "更新成功");
    }

    @DeleteMapping(value = "/{spitId}")
    public WebResult deleteById(@PathVariable String spitId) {
        spitService.deleteById(spitId);
        return new WebResult(StatusCode.OK, true, "删除成功");
    }

    @GetMapping(value = "/comment/{parentId}/{page}/{size}")
    public WebResult findByParentId(@PathVariable String parentId, @PathVariable int page, @PathVariable int size) {
        return new WebResult(StatusCode.OK, true, "查询成功", spitService.findByParentId(parentId, page, size));
    }

    @PutMapping(value = "/thumbup/{spitId}")
    public WebResult thumbup(@PathVariable String spitId){
        //判断当前用户是否已经点赞
        String userId="111";
        if(redisTemplate.opsForValue().get("thumbup_"+userId)!=null){
            return new WebResult(StatusCode.OK, true, "不能重复点赞");
        }
        spitService.thumbup(spitId);
        redisTemplate.opsForValue().set("thumbup_"+userId,1);
        return new WebResult(StatusCode.OK, true, "点赞成功");
    }
}
