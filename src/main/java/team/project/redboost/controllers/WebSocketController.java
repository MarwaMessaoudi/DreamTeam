package team.project.redboost.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public String handleHelloMessage(String message) {
        System.out.println("Message reçu : " + message);
        return "Réponse : " + message;
    }
}

