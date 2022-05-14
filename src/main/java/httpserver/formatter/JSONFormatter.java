package httpserver.formatter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.IFile;
import entity.IFolder;
import entity.ISymLink;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class JSONFormatter implements IFormatter {
    @Override
    public byte[] formatFolder(List<IFolder> folders, List<IFile> files, List<ISymLink> links) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String foldersString = mapper.writeValueAsString(folders);
        String filesString = mapper.writeValueAsString(files);
        String linksString = mapper.writeValueAsString(links);
        StringBuilder stringBuilder = new StringBuilder(foldersString);
        stringBuilder.insert(0, "{\"result\": { \"folders\": ");
        stringBuilder.insert(stringBuilder.length(), ", \"files\": " + filesString);
        stringBuilder.insert(stringBuilder.length(), ", \"links\": ");
        stringBuilder.insert(stringBuilder.length(), linksString + "}}");
        return stringBuilder.toString().getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public byte[] formatError(String error) {
        return ("{ \"error\": \"true\", \"message\": \"" + error + "\" }").getBytes();
    }
}
