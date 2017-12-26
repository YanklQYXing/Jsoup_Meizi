package com.kangle.meizipictures.model;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by Administrator on 2017/12/18.
 */

@Table(name="video")
public class VideoModel {
    @Column(name = "id", isId = true)
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @Column(name = "vid")
    String vId; // 电影编号

    @Column(name = "name")
    String name; // 电影名字

    @Column(name = "path")
    String path; // 电影详情网址

    @Column(name = "type")
    String type; // 视频分类

    @Column(name = "imgUrl")
    String imgUrl; // 视频图片地址

    @Column(name = "imgUrl")
    String videoUrl; // 视频地址

    public String getImgUrl() {
        if (imgUrl==null){
            return "";
        }
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getVideoUrl() {
        if (videoUrl==null){
            return "";
        }
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getType() {
        if (type==null){
            return "";
        }
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        if (name==null){
            return "";
        }
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        if (path==null){
            return "";
        }
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getvId() {
        return vId;
    }

    public void setvId(String vId) {
        this.vId = vId;
    }
}
