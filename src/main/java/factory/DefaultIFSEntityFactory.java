package factory;

import entity.*;

import java.util.HashMap;

public class DefaultIFSEntityFactory implements AbstractIFSEntityFactory {
    @Override
    public IFolder createFolder(String name) {
        return new Folder(name, new HashMap<String, IFSEntity>());
    }

    @Override
    public IFile createFile(String name, byte[] content) {
        return new File(name, content);
    }

    @Override
    public ISymLink createSymLink(String name, String path) {
        return new SymLink(name, path);
    }
}
