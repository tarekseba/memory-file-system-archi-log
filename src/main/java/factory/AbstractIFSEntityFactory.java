package factory;

import entity.IFile;
import entity.IFolder;
import entity.ISymLink;

public interface AbstractIFSEntityFactory {
    IFolder createFolder(String name);

    IFile createFile(String name, byte[] content);

    ISymLink createSymLink(String name, String path);
}
