package facade;

import entity.IFSEntity;
import entity.Root;

public class FileSystemImpl implements IFileSystem {
    private Root root;



    @Override
    public IFSEntity getElement(String path) {
        String[] pathSplit = path.split("/");
        if(pathSplit.length == 1) {
            return root;
        } else if(pathSplit.length > 1) {

            return root.getElement(pathSplit, 1);
        }
        return null;
    }

    @Override
    public int addElement(IFSEntity element, String path) {
        return 0;
    }

    @Override
    public int removeElement(String path) {
        return 0;
    }
}
