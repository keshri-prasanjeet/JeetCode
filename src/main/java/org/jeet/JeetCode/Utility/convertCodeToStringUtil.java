package org.jeet.JeetCode.Utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class convertCodeToStringUtil {

    private final ObjectMapper objectMapper;

    public convertCodeToStringUtil(ObjectMapper objectMapper){
        this.objectMapper=objectMapper;
    }

    public void convertCode(String source) throws JsonProcessingException {
        String jsonPayload = objectMapper.writeValueAsString(source);
        System.err.println(jsonPayload);
    }
}
