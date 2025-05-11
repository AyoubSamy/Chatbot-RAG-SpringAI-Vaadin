package net.ayoub.projet_professionnel_chatbot_essaie.config;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.document.Document;


import java.io.File;
import java.nio.file.Path;
import java.util.List;



@Component
public class RagDataLoader {
    @Value("classpath:/rag_documents/test_rag.pdf")
    private Resource pdfResource;

    @Value("store-data-v1.json")
    private String storeFile;
    @Bean
    public SimpleVectorStore simpleVectorStore(EmbeddingModel embeddingModel){
         SimpleVectorStore vectorStore = SimpleVectorStore.builder(embeddingModel).build();

         String fileStore = Path.of("src","main","resources","store")
                 .toAbsolutePath()+"/"+storeFile;

         File file = new File(fileStore);
         if(!file.exists()){
            PagePdfDocumentReader pagePdfDocumentReader=
                    new PagePdfDocumentReader(pdfResource);
             List<Document> documents = pagePdfDocumentReader.get();

             TextSplitter textSplitter = new TokenTextSplitter();
             List<Document> chunks = textSplitter.split(documents);
             vectorStore.accept(chunks);
             vectorStore.save(file);
         } else {
             // si ona deja nos deocument dans notre vectore store il ne faut que les charger
         vectorStore.load(file);
         }
            return vectorStore;
         }

}
