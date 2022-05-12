package factory;

import entity.File;
import entity.Folder;
import entity.SymLink;

public interface AbstractIFSEntityFactory {
    Folder createFolder(String name);

    File createFile(String name, byte[] content);

    SymLink createSymLink(String name, String path);
}
