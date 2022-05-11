package factory;

import entity.SymLink;
import entity.File;
import entity.Folder;

public interface AbstractIFSEntityFactory {
    Folder createFolder(String name);
    File createFile(String name, byte[] content);
    SymLink createSymLink(String name, String path);
}
