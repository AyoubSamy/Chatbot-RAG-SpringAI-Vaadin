package net.ayoub.projet_professionnel_chatbot_essaie.web;

import net.ayoub.projet_professionnel_chatbot_essaie.Services.ChatService ;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;

@RestController // est une classe specifique qui peut traiter les requete http arriver via le browser ou via le front end et fournit des reponse
// gneralement sous la forme json

@RequestMapping("/chat") //dit a sring quand une requete vient d'apres ce path "/chat" executer le controller ou la methode

public class chatRestController {

    private ChatService chatAIService;

    public chatRestController(ChatService chatAIService) {
        this.chatAIService = chatAIService;
    }

    @GetMapping(value = "/ask",produces = MediaType.TEXT_PLAIN_VALUE)
    public String ask(String question){
        return chatAIService.RagChat(question);
    }


}
