package entity;

public interface IFolder extends IFSEntity {
    IFSEntity getElement(String[] path, int i);

    boolean addElement(IFSEntity entity);

    boolean removeElement(String[] path, int i);
}
