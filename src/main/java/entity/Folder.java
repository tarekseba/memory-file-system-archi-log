package entity;

import java.util.HashMap;
import java.util.Map;

public class Folder implements IFolder {
    private Name name;
    private Map<String, IFolder> folders;
    private Map<String, IFile> files;
    private Map<String, ISymLink> links;
    private FILE_TYPE type;

    public Folder(String name) throws IllegalArgumentException {
        this.name = new Name(name);
        this.folders = new HashMap<>();
        this.files = new HashMap<>();
        this.links = new HashMap<>();
        type = FILE_TYPE.FOLDER;
    }

    @Override
    public int getSize() {
        int size = 0;
        for (String fs : folders.keySet()
        ) {
            size += folders.get(fs).getSize();
        }
        return size;
    }

    @Override
    public String getName() {
        return name.getName();
    }

    @Override
    public FILE_TYPE getType() {
        return this.type;
    }

    @Override
    public IFolder getFolder(String[] path, int index) {
        IFolder entity = this.folders.get(path[index]);
        if (entity != null) {
            if (index == path.length - 1) {
                return entity;
            } else if (entity.getType().equals(FILE_TYPE.FOLDER)) {
                return entity.getFolder(path, ++index);
            }
        }
        return null;
    }

    public IFile getFile(String[] path, int index) {
        if (index == path.length - 1) {
            IFile entity = this.files.get(path[index]);
            return entity;
        } else {
            IFolder folder = this.folders.get(path[index]);
            return folder.getFile(path, ++index);
        }
    }

    @Override
    public ISymLink getSymLink(String[] path, int index) {
        if (index == path.length - 1) {
            ISymLink entity = this.links.get(path[index]);
            return entity;
        } else {
            IFolder folder = this.folders.get(path[index]);
            return folder.getSymLink(path, ++index);
        }
    }

    @Override
    public boolean addFolder(IFolder entity) {
        if (!this.files.containsKey(entity.getName()) && !this.folders.containsKey(entity.getName()) && !this.links.containsKey(entity.getName()))
            return (folders.put(entity.getName(), entity) != null);
        return false;
    }

    @Override
    public boolean addFile(IFile entity) {
        if (!this.files.containsKey(entity.getName()) && !this.folders.containsKey(entity.getName()) && !this.links.containsKey(entity.getName()))
            return (files.put(entity.getName(), entity) != null);
        return false;
    }

    @Override
    public boolean addSymLink(ISymLink entity) {
        if (!this.links.containsKey(entity.getName()) && !this.folders.containsKey(entity.getName()) && !this.links.containsKey(entity.getName()))
            return (links.put(entity.getName(), entity) != null);
        return false;
    }

    @Override
    public boolean removeElement(String[] path, int index) {
        System.out.println("LOL");
        if (index == path.length - 1) {
            if (this.folders.remove(path[index]) != null || this.files.remove(path[index]) != null || this.links.remove(path[index]) != null)
                return true;
        } else {
            IFolder entity = this.folders.get(path[index]);
            if (entity != null)
                return entity.removeElement(path, ++index);
        }
        return false;
    }

    @Override
    public Map<String, IFolder> getFolders() {
        return this.folders;
    }

    @Override
    public Map<String, IFile> getFiles() {
        return this.files;
    }

    @Override
    public Map<String, ISymLink> getSymLinks() {
        return this.links;
    }
}
