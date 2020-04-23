package com.lim.poly.springboot.web;

import com.lim.poly.springboot.service.chat.ChatService;
import com.lim.poly.springboot.util.CmmUtil;
import com.lim.poly.springboot.util.DateUtil;
import com.lim.poly.springboot.web.dto.ChatDto;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@RequiredArgsConstructor
@RestController
public class ChatController {

    private final Logger log=Logger.getLogger(String.valueOf(this.getClass()));
    private final ChatService chatService;

    @PostMapping("/chat/roomList")
    public Set<String> roomList(HttpServletRequest request, HttpServletResponse response) throws Exception{
        log.info(this.getClass().getName()+".roomList Start!");

        Set<String> rSet=chatService.getRoomList();

        if(rSet==null){
            rSet=new LinkedHashSet<String>();
        }

        log.info(this.getClass().getName()+".roomList End!");

        return rSet;
    }

    @PostMapping("/chat/msg")
    public List<ChatDto> msg(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) throws Exception{
        log.info(this.getClass().getName()+".msg Start!");

        String room_name= CmmUtil.nvl((String)session.getAttribute("SS_ROOM_NAME"));
        String userNm=CmmUtil.nvl((String)session.getAttribute("SS_USER_NAME"));
        model.addAttribute("room_name", room_name);
        String msg=CmmUtil.nvl(request.getParameter("send_msg"));

        log.info("usrNm:"+userNm);
        log.info("room_name:"+room_name);
        log.info("msg:"+msg);

        List<ChatDto> rList=null;

        if(msg.length()>0){
            ChatDto chatDto=new ChatDto();
            chatDto.setRoomKey("Chat_"+room_name);
            chatDto.setUserNm(userNm);
            chatDto.setMsg(msg);
            chatDto.setDateTime(DateUtil.getDateTime("yyyy.MM.dd hh:mm:ss"));

            rList=chatService.insertChat(chatDto);

            if(rList==null){
                rList=new LinkedList<ChatDto>();
            }
            chatDto=null;
        }

        log.info(this.getClass().getName()+".msg End!");

        return rList;
    }

    @PostMapping("/chat/getMsg")
    public List<ChatDto> getMsg(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) throws Exception{
        log.info(this.getClass().getName()+".getMsg Start!");

        String room_name=CmmUtil.nvl((String)session.getAttribute("SS_ROOM_NAME"));
        model.addAttribute("room_name", room_name);

        log.info("room_name:"+room_name);

        ChatDto chatDto=new ChatDto();
        chatDto.setRoomKey("Chat_"+room_name);

        List<ChatDto> rList=chatService.getChat(chatDto);

        if(rList==null){
            rList=new LinkedList<ChatDto>();
        }
        chatDto=null;

        log.info(this.getClass().getName()+".getMsg End!");

        return rList;
    }

}
