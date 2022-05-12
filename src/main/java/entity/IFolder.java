package entity;

import java.util.Map;

public interface IFolder extends IFSEntity {

    IFolder getFolder(String[] path, int i);

    IFile getFile(String[] path, int i);

    ISymLink getSymLink(String[] path, int i);

    boolean addFolder(IFolder entity);

    boolean addFile(IFile entity);

    boolean addSymLink(ISymLink entity);

    boolean removeElement(String[] path, int i);

    Map<String, IFolder> getFolders();

    Map<String, IFile> getFiles();

    Map<String, ISymLink> getSymLinks();
}
