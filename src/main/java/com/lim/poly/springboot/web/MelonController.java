package com.lim.poly.springboot.web;

import com.lim.poly.springboot.service.melon.MelonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;

@RequiredArgsConstructor
@RestController
public class MelonController {

    private Logger log = Logger.getLogger(String.valueOf(this.getClass()));
    private final MelonService melonService;

    @GetMapping("/melon/collect")
    public String collectMelon(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info(this.getClass().getName() + ".collectMelon start!");

        melonService.collectMelonRank();

        log.info(this.getClass().getName() + ".collectMelon end!");

        return "success";
    }
}
