package httpserver.formatter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.IFSEntity;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class JSONFormatter implements IFormatter {
    @Override
    public byte[] formatFolder(List<IFSEntity> entities) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(entities);
        StringBuilder stringBuilder = new StringBuilder(jsonString);
        stringBuilder.insert(0, "{\"result\": ");
        stringBuilder.insert(stringBuilder.length(), "}");
        return stringBuilder.toString().getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public byte[] formatFile(IFSEntity entity) {
        return new byte[0];
    }

    @Override
    public byte[] formatError(String error) {
        return ("{ \"error\": \"true\", \"message\": \"" + error + "\" }").getBytes();
    }
}
