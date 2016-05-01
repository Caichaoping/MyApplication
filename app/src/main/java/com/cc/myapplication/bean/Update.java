package com.cc.myapplication.bean;

import java.util.Date;

/**
 * 注释：更新实体类
 * 作者：菠菜 on 2016/5/1 11:53
 * 邮箱：971859818@qq.com
 */
public class Update {

    private Date created_at;

    private String description;

    private String download_url;

    private int num_version;

    private String relevance_url;

    private Date updated_at;

    private String version;

    private int version_type;

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDownload_url() {
        return download_url;
    }

    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }

    public int getNum_version() {
        return num_version;
    }

    public void setNum_version(int num_version) {
        this.num_version = num_version;
    }

    public String getRelevance_url() {
        return relevance_url;
    }

    public void setRelevance_url(String relevance_url) {
        this.relevance_url = relevance_url;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getVersion_type() {
        return version_type;
    }

    public void setVersion_type(int version_type) {
        this.version_type = version_type;
    }
}
