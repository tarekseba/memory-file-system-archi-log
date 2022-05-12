package httpserver.formatter;

import com.fasterxml.jackson.core.JsonProcessingException;
import entity.IFSEntity;
import entity.IFile;
import entity.IFolder;
import entity.ISymLink;

import java.util.List;

public interface IFormatter {
    byte[] formatFolder(List<IFolder> folders, List<IFile> files, List<ISymLink> links) throws JsonProcessingException;

    byte[] formatFile(IFSEntity content);

    byte[] formatError(String error);
}
