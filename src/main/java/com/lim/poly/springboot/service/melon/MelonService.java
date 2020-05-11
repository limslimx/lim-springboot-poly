package com.lim.poly.springboot.service.melon;

import com.lim.poly.springboot.repository.melon.MelonMapper;
import com.lim.poly.springboot.util.DateUtil;
import com.lim.poly.springboot.web.dto.MelonDto;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

@RequiredArgsConstructor
@Service
public class MelonService {

    private Logger log = Logger.getLogger(String.valueOf(this.getClass()));
    private final MelonMapper melonMapper;

    public int collectMelonRank() throws Exception {
        log.info(this.getClass().getName() + ".collectMelonRank start!");

        int res = 0;

        List<MelonDto> melonList = new ArrayList<MelonDto>();

        String url = "https://www.melon.com/chart/index.htm";

        Document doc = null;
        doc = Jsoup.connect(url).get();

        Elements element = doc.select("div.service_list_song");

        Iterator<Element> rank50List = element.select("tr.lst50").iterator();

        while (rank50List.hasNext()) {
            Element songInfo = rank50List.next();

            String rank = songInfo.select("span.rank").text();
            String song = songInfo.select("div.ellipsis a").eq(0).text();
            String singer = songInfo.select("div.ellipsis a").eq(1).text();
            String album = songInfo.select("div.ellipsis a").eq(3).text();

            songInfo = null;

            MelonDto melonDto = new MelonDto();
            melonDto.setCollect_time(DateUtil.getDateTime("yyyyMMddhhmmss"));
            melonDto.setRank(rank);
            melonDto.setSong(song);
            melonDto.setSinger(singer);
            melonDto.setAlbum(album);

            melonList.add(melonDto);
        }
        String colNm = "MelonTOP100_" + DateUtil.getDateTime("yyyyMMdd");
        melonMapper.createCollection(colNm);
        melonMapper.insertRank(melonList, colNm);

        log.info(this.getClass().getName() + ".collectMelonRank end!");

        return res;
    }

    public List<MelonDto> getRank() throws Exception {
        log.info(this.getClass().getName() + ".getRank start!");

        String colNm = "MelonTOP100_" + DateUtil.getDateTime("yyyyMMdd");

        List<MelonDto> melonDtoList = melonMapper.getRank(colNm);

        if (melonDtoList == null) {
            melonDtoList = new ArrayList<MelonDto>();
        }
        log.info(this.getClass().getName() + ".getRank end!");

        return melonDtoList;
    }

}
