package httpserver.formatter;

import com.fasterxml.jackson.core.JsonProcessingException;
import entity.IFSEntity;

import java.util.List;

public interface IFormatter {
    byte[] formatFolder(List<IFSEntity> entities) throws JsonProcessingException;

    byte[] formatFile(IFSEntity content);

    byte[] formatError(String error);
}
