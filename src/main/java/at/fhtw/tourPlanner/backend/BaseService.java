package at.fhtw.tourPlanner.backend;
import at.fhtw.tourPlanner.model.Entry;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public abstract class BaseService {
    private ObjectMapper objectMapper;

    public BaseService() {
        this.objectMapper = new ObjectMapper();
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public String entryToJson(Entry entry){
        try{
            this.getObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
            return this.getObjectMapper().writeValueAsString(entry);
        }
        catch(JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }

    public Entry JsonToEntry(String json){
        try{
            return getObjectMapper().readValue(json, new TypeReference<Entry>(){});
        }
        catch(JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }
}
