package by.fpmibsu.bielrent.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonMapper {
    private ObjectMapper mapper = new ObjectMapper();

    private static JsonMapper INSTANCE = new JsonMapper();

    public static JsonMapper getInstance() {
        return INSTANCE;
    }

    public <T> String ToJson(T javaObj) {
        try {
            return mapper.writeValueAsString(javaObj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T FromJson(String jsonObj, Class<T> clazz) {
        try {
            return mapper.readValue(jsonObj.getBytes(StandardCharsets.UTF_8), clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
