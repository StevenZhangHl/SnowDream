package com.example.zealience.oneiromancy.util;

import com.example.zealience.oneiromancy.entity.HomeNormalEntity;
import com.example.zealience.oneiromancy.entity.HomeRecommendEntity;
import com.example.zealience.oneiromancy.entity.VideoEntity;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @user steven
 * @createDate 2019/3/21 10:47
 * @description 手动解析特定数据
 */
public class ParseJsonUtil {
    public static HomeRecommendEntity parseRecomendEntity(JsonObject jsonObject) {
        HomeRecommendEntity entity = new HomeRecommendEntity();
        if (jsonObject.has("videoEntity")) {
            VideoEntity videoEntity = parseVideoEntity(jsonObject.get("videoEntity").getAsJsonObject());
            entity.setVideoEntity(videoEntity);
        }
        if (jsonObject.has("portraitList")) {
            HomeNormalEntity portrait = parseNormalEntity(jsonObject.get("portraitList").getAsJsonObject());
            entity.setPortraitEntity(portrait);
        }
        if (jsonObject.has("horizontalList")) {
            List<HomeNormalEntity> normalEntities = new ArrayList<>();
            for (int i = 0; i < jsonObject.get("horizontalList").getAsJsonArray().size(); i++) {
                HomeNormalEntity normalEntity = parseNormalEntity(jsonObject.get("horizontalList").getAsJsonArray().get(i).getAsJsonObject());
                normalEntities.add(normalEntity);
            }
            entity.setHorizontalEntity(normalEntities);
        }
        if (jsonObject.has("type")) {
            entity.setType(jsonObject.get("type").getAsInt());
        }
        return entity;
    }

    private static VideoEntity parseVideoEntity(JsonObject jsonObject) {
        VideoEntity videoEntity = new VideoEntity();
        if (jsonObject.has("title")) {
            videoEntity.setTitle(jsonObject.get("title").getAsString());
        }
        if (jsonObject.has("videoThumb")) {
            videoEntity.setVideoThumb(jsonObject.get("videoThumb").getAsString());
        }
        if (jsonObject.has("videoUrl")) {
            videoEntity.setVideoUrl(jsonObject.get("videoUrl").getAsString());
        }
        return videoEntity;
    }

    public static HomeNormalEntity parseNormalEntity(JsonObject asJsonObject) {
        HomeNormalEntity normalEntity = new HomeNormalEntity();
        if (asJsonObject.has("userName")) {
            normalEntity.setUserName(asJsonObject.get("userName").getAsString());
        }
        if (asJsonObject.has("userImage")) {
            normalEntity.setUserImage(asJsonObject.get("userImage").getAsString());
        }
        if (asJsonObject.has("photoUrl")) {
            normalEntity.setPhotoUrl(asJsonObject.get("photoUrl").getAsString());
        }
        if (asJsonObject.has("content")) {
            normalEntity.setContent(asJsonObject.get("content").getAsString());
        }
        return normalEntity;
    }
}
