package com.lim.poly.springboot.web;

import com.lim.poly.springboot.service.GetSetService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private Logger log = Logger.getLogger(String.valueOf(this.getClass()));
    private final GetSetService getSetService;

    @GetMapping("/")
    public String index(){
        getSetService.test();
        return getSetService.test();
    }

    @RequestMapping("/rank/index")
    public String index(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info(this.getClass().getName() + ".index start!");
        log.info(this.getClass().getName() + ".index end!");
        return "/rank/index";
    }

}
