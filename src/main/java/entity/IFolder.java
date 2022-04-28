package entity;

public interface IFolder extends IFSEntity{
    IFSEntity getElement(String[] path, int i);
}
