package com.wist.tensquare.article.web.controller;

import java.util.Map;

import com.wist.tensquare.article.pojo.Channel;
import com.wist.tensquare.article.service.ChannelService;
import entity.PageResult;
import entity.StatusCode;
import entity.WebResult;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 控制器层
 *
 * @author BoBoLaoShi
 */
@RestController
@CrossOrigin
@RequestMapping("/channel")
public class ChannelController {

    @Resource
    private ChannelService channelService;


    /**
     * 增加
     *
     * @param channel
     */
    @PostMapping
    public WebResult add(@RequestBody Channel channel) {
        channelService.saveChannel(channel);
        return new WebResult(StatusCode.OK, true, "增加成功");
    }

    /**
     * 修改
     *
     * @param channel
     */
    @PutMapping("/{id}")
    public WebResult edit(@RequestBody Channel channel, @PathVariable String id) {
        channel.setId(id);
        channelService.updateChannel(channel);
        return new WebResult(StatusCode.OK, true, "修改成功");
    }

    /**
     * 删除
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    public WebResult remove(@PathVariable String id) {
        channelService.deleteChannelById(id);
        return new WebResult(StatusCode.OK, true, "删除成功");
    }

    /**
     * 查询全部数据
     *
     * @return
     */
    @GetMapping
    public WebResult list() {
        return new WebResult(StatusCode.OK, true, "查询成功", channelService.findChannelList());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @GetMapping("/{id}")
    public WebResult listById(@PathVariable String id) {
        return new WebResult(StatusCode.OK, true, "查询成功", channelService.findChannelById(id));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     * @return
     */
    @PostMapping("/search")
    public WebResult list(@RequestBody Map searchMap) {
        return new WebResult(StatusCode.OK, true, "查询成功", channelService.findChannelList(searchMap));
    }

    /**
     * 分页+多条件查询
     *
     * @param searchMap 查询条件封装
     * @param page      页码
     * @param size      页大小
     * @return 分页结果
     */
    @PostMapping("/search/{page}/{size}")
    public WebResult listPage(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page<Channel> pageResponse = channelService.findChannelListPage(searchMap, page, size);
        return new WebResult(StatusCode.OK, true, "查询成功", new PageResult<Channel>(pageResponse.getTotalElements(), pageResponse.getContent()));
    }

}
