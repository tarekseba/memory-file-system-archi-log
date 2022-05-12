package factory;

import entity.*;

public class DefaultIFSEntityFactory implements AbstractIFSEntityFactory {
    @Override
    public IFolder createFolder(String name) {
        return new Folder(name);
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
