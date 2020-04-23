package com.lim.poly.springboot.web;

import com.lim.poly.springboot.service.chat.ChatService;
import com.lim.poly.springboot.util.CmmUtil;
import com.lim.poly.springboot.util.DateUtil;
import com.lim.poly.springboot.web.dto.ChatDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.logging.Logger;

@RequiredArgsConstructor
@Controller
public class MainController {

    private final Logger log=Logger.getLogger(String.valueOf(this.getClass()));
    private final ChatService chatService;


    @GetMapping("/tts")
    public String tts(){
        return "chat/TTS";
    }

    @GetMapping("/chat/index")
    public String index() throws Exception{
        log.info(this.getClass().getName()+".index() Start!!");
        log.info(this.getClass().getName()+".insertChat() End!!");
        return "chat/index";
    }

    @PostMapping("/chat/intro")
    public String intro(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) throws Exception{
        log.info(this.getClass().getName()+".intro() Start!!");

        session.setAttribute("SS_ROOM_NAME", "");
        session.setAttribute("SS_USER_NAME", "");

        String room_name= CmmUtil.nvl(request.getParameter("room_name"));
        String userNm=CmmUtil.nvl(request.getParameter("user_name"));

        log.info("userNm: "+userNm);
        log.info("room_name: "+room_name);

        session.setAttribute("SS_ROOM_NAME", room_name);
        session.setAttribute("SS_USER_NAME", userNm);

        model.addAttribute("room_name", session.getAttribute("SS_ROOM_NAME"));

        ChatDto chatDto=new ChatDto();
        chatDto.setRoomKey("Chat_"+room_name);
        chatDto.setUserNm("관리자.");
        chatDto.setMsg(userNm+"님! ["+room_name+"] 채팅방 입장을 환영합니다.");
        chatDto.setDateTime(DateUtil.getDateTime("yyyy.MM.dd hh:mm:ss"));

        chatService.insertChat(chatDto);

        log.info(this.getClass().getName()+".intro() End!!");

        return "/chat/intro";
    }
}
