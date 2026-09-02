package com.example.backend.security.indexing;

import com.example.backend.security.ai.RagSettings;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;


@Component
public class CodeChunker {
    private final TokenTextSplitter tokenTextSplitter;
    private final CodeFileFilter codeFileFilter;

    public CodeChunker(@Value("${app.indexing.chunk-size:800}") int chunkSize , CodeFileFilter fileFilter){
        int chunkTokens = Math.max(50,chunkSize/4);
        this.tokenTextSplitter = TokenTextSplitter.builder().withChunkSize(chunkTokens).build();
        this.codeFileFilter = fileFilter;
    }

    public List<Document> chunkFile(String repoId , String filePath , String context){
        if(context == null || context.isBlank()){
            return List.of();
        }
        String language = codeFileFilter.detectLanguage(filePath);
        String header = "// File: "+filePath+"\n";
        Document source =  new Document(header + context , baseMetadata(repoId,filePath,language))
        List<Document> split = tokenTextSplitter.apply(List.of(source));
        return IntStream.range(i -> withChunkIndex(split.get(i), repoId, filePath, language , i)).toList();
        }

        private static Map<String , Object> baseMetadata (String repoId , String filePath , String Language){
        Map<String , Object> metadata = new HashMap<>();
        metadata.put(RagSettings.METADATA_REPO_ID, repoId);
        metadata.put("filePath",filePath);
        metadata.put("language",Language);
        return metadata;
        }

        private static Document withChunkIndex(Document chunk , String repoId , String filePath , String language , int chunkIndex){
        Map<String ,Object> metadata = new HashMap<>(chunk.getMetadata());
        metadata.put(RagSettings.METADATA_REPO_ID,repoId);
        metadata.put("filePath",filePath);
        metadata.put("language",language);
        metadata.put("chunkIndex",chunkIndex);
        return new Document(chunk.getText(),metadata);
        }
}
