package net.ayoub.projet_professionnel_chatbot_essaie.Services;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

// dans java le symbole @ au debut d'un nom signifie la declaration d'une annotation
// Une annotation est une métadonnée (une information supplémentaire) que tu ajoutes
// à ton code pour donner des instructions spéciales au compilateur, au framework, ou à l’environnement d’exécution.

@BrowserCallable // permet aux methodes java qui sont dans la partie backend  to be injecter directement dans la partie client
// sans setuper manuellement les parametres REST APIs oou endpoints
@AnonymousAllowed //authoriser l'acces annonyme pour ne pas forcement passer par l''authentification

public class ChatService {
    private ChatClient chatClient ; // pour se connecter a une partie serveur pour envoyer des question et recuperer des reponses

    public ChatService(ChatClient.Builder builder){
        this.chatClient= builder.build();

    }
    public String RagChat(String question){
        return chatClient.prompt() // prompter le client c-a-d le LLM
                .user(question)
                .call()
                .content();
    }
}
