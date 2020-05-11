package com.lim.poly.springboot.domain.melon;

import com.lim.poly.springboot.service.melon.MelonService;
import com.lim.poly.springboot.util.DateUtil;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Getter
@Document
public class Melon {

    @Id
    private String id;

    private String collect_time;

    private String rank;

    private String song;

    private String singer;

    private String album;

    @Builder
    public Melon(String id, String collect_time, String rank, String song, String singer, String album) {
        this.id = id;
        this.collect_time = collect_time;
        this.rank = rank;
        this.song = song;
        this.singer = singer;
        this.album = album;
    }
}
