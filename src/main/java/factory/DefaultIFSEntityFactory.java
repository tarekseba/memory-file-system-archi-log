package factory;

import entity.*;

import java.util.HashMap;

public class DefaultIFSEntityFactory implements AbstractIFSEntityFactory {
    @Override
    public Folder createFolder(String name) {
        return new Folder(name, new HashMap<String, IFSEntity>());
    }

    @Override
    public File createFile(String name, byte[] content) {
        return new File(name, content);
    }

    @Override
    public SymLink createSymLink(String name, String path) {
        return new SymLink(name, path);
    }
}
