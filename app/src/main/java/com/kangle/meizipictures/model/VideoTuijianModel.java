package com.kangle.meizipictures.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/18.
 */

public class VideoTuijianModel {

    List<VideoModel> lunbos;
    List<TypeVideos> typeVideoses;

    public List<VideoModel> getLunbos() {
        if (lunbos==null){
            return new ArrayList<>();
        }
        return lunbos;
    }

    public void setLunbos(List<VideoModel> lunbos) {
        this.lunbos = lunbos;
    }

    public List<TypeVideos> getTypeVideoses() {
        if (typeVideoses==null){
            return new ArrayList<>();
        }
        return typeVideoses;
    }

    public void setTypeVideoses(List<TypeVideos> typeVideoses) {
        this.typeVideoses = typeVideoses;
    }

    class TypeVideos{
        List<VideoModel> videoModels;
        String name;

        public String getName() {
            if (name==null){
                return "name";
            }
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<VideoModel> getVideoModels() {

            if (videoModels==null){
                return new ArrayList<>();
            }
            return videoModels;
        }

        public void setVideoModels(List<VideoModel> videoModels) {
            this.videoModels = videoModels;
        }
    }

}
