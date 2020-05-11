package com.lim.poly.springboot.web.dto;

import com.lim.poly.springboot.domain.melon.Melon;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MelonDto {

    private String id;

    private String collect_time;

    private String rank;

    private String song;

    private String singer;

    private String album;

    public Melon toEntity() {
        return Melon.builder()
                .id(id)
                .collect_time(collect_time)
                .rank(rank)
                .song(song)
                .singer(singer)
                .album(album)
                .build();
    }
}
