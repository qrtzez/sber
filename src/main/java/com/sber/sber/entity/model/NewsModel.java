package com.sber.sber.entity.model;

import com.sber.sber.entity.Human;
import com.sber.sber.entity.News;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@SuperBuilder
public class NewsModel {

    private String title;

    public static NewsModel toModel(News news) {
        return NewsModel.builder()
                .title(news.getTitle())
                .build();
    }

    public static List<NewsModel> toModelList(List<News> news) {
        return news.stream().map(NewsModel::toModel).collect(Collectors.toList());
    }
}
