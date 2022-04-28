package facade;

import entity.IFSEntity;

public interface IFileSystem {
    IFSEntity getElement(String path);
    int addElement(IFSEntity element,String path);
    int removeElement(String path);
}
