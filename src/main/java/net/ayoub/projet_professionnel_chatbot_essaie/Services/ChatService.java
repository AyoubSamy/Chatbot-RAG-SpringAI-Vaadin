package net.ayoub.projet_professionnel_chatbot_essaie.Services;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.ai.document.Document;
import java.util.List;
import java.util.Map;

// dans java le symbole @ au debut d'un nom signifie la declaration d'une annotation
// Une annotation est une métadonnée (une information supplémentaire) que tu ajoutes
// à ton code pour donner des instructions spéciales au compilateur, au framework, ou à l’environnement d’exécution.

@BrowserCallable // permet aux methodes java qui sont dans la partie backend  to be injecter directement dans la partie client
// sans setuper manuellement les parametres REST APIs oou endpoints
@AnonymousAllowed //authoriser l'acces annonyme pour ne pas forcement passer par l''authentification

public class ChatService {
    private ChatClient chatClient ;// pour se connecter a une partie serveur pour envoyer des question et recuperer des reponses
    private VectorStore vectorStore;
    @Value("classpath:/prompts/prompt-template.st")
    private Resource PromptResource;

    public ChatService(ChatClient.Builder builder,VectorStore vectorStore){
        this.chatClient= builder.build();
        this.vectorStore=vectorStore;
    }

    public String RagChat(String question){
        List<Document> documents = vectorStore.similaritySearch(question); // chercher le contexte sous forme d'une liste de documents en fesant
        // une recherche par similarity (le texte qui est plus proche en terme de sense a la question prompter)

        List<String> contexte = documents.stream().map(Document::getText).toList();

        PromptTemplate promptTemplate = new PromptTemplate(PromptResource);

        Prompt prompt = promptTemplate.create(Map.of("context",contexte,"question",question)); // injection des donner dans le fichier prompt-template.st remplire la variable
        //contexte en donnant le contexte recuperer dans data externe et la question a repondre

        return chatClient.prompt(prompt) // prompter le client c-a-d le LLM
                .user(question)
                .call()
                .content();
    }
}
