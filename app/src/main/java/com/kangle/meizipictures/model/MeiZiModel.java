package com.kangle.meizipictures.model;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by Administrator on 2017/7/9.
 */

@Table(name = "meizi")
public class MeiZiModel {

    @Column(name = "id", isId = true)
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "yare")
    String yare; // 年编号
    @Column(name = "num")
    String num; // 人的编号
    @Column(name = "pictureUrl")
    String pictureUrl; // 图片地址
    @Column(name = "name")
    String name; // 标题
    @Column(name="type")
    int type = 0;  // 数据类型，0浏览记录，1 收藏

    public String getYare() {
        if (yare==null){
            return "";
        }
        return yare;
    }

    public void setYare(String yare) {
        this.yare = yare;
    }

    public String getNum() {
        if (num==null){
            return "";
        }
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getPictureUrl() {
        if (pictureUrl==null){
            return "";
        }
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getName() {
        if (name==null){
            return "妹子";
        }
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
