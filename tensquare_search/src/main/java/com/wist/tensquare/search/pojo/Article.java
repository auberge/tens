package com.wist.tensquare.search.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.Serializable;
import java.util.Date;

/**
 * @author simptx
 */
@Document(indexName = "tensquare_article", type = "article")
public class Article implements Serializable {
    @Id
    private String id;
    /**
     * 是否索引，就是看该域是否能被搜索
     * 是否分词，表示搜索的时候是整体匹配还是分词匹配
     * 是否存储，表示是否再页面中显示
     */
    @Field(index = true,analyzer="ik_max_word",searchAnalyzer="ik_max_word")
    private String title;
    @Field(index = true)
    private String content;
    private String stats; //审核状态
    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStats() {
        return stats;
    }

    public void setStats(String stats) {
        this.stats = stats;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
