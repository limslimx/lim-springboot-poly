package com.lim.poly.springboot.service.chat;

import com.lim.poly.springboot.repository.chat.ChatRepository;
import com.lim.poly.springboot.util.CmmUtil;
import com.lim.poly.springboot.web.dto.ChatDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@RequiredArgsConstructor
@Service
public class ChatService {

    private final Logger log=Logger.getLogger(String.valueOf(this.getClass()));
    private final ChatRepository chatRepository;

    public Set<String> getRoomList() throws Exception{
        log.info(this.getClass().getName()+".getRoomList() Start!!");

        return chatRepository.getRoomList();
    }

    public List<ChatDto> insertChat(ChatDto chatDto) throws Exception{
        log.info(this.getClass().getName()+".insertChat() Start!!");

        if(chatRepository.insertChat(chatDto)==1){
            log.info("chatMapper.insertChat success!");
            chatRepository.setTimeOutMinute(CmmUtil.nvl(chatDto.getRoomKey()), 5);
        }else {
            log.info("chatMapper.insertChat fail!");
        }

        log.info(this.getClass().getName()+".insertChat() End!!");
        return getChat(chatDto);
    }

    public List<ChatDto> getChat(ChatDto chatDto) throws Exception{
        log.info(this.getClass().getName()+".getChat() Start!!");
        return chatRepository.getChat(chatDto);
    }
}
