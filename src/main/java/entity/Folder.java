package entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class Folder implements IFolder{
    private  Name name;
    private Map<String,IFSEntity> content;
    private FILE_TYPE type;

    public Folder(String name, Map<String,IFSEntity> content) throws IllegalArgumentException {
        this.name = new Name(name);
        this.content = content;
        type= FILE_TYPE.FOLDER;
    }

    public Map<String, IFSEntity> getContent() {
        return content;
    }

    public void setContent(Map<String, IFSEntity> content) {
        this.content = content;
    }

    /*@Override
    public String toString() {
        return "Folder{" +
                "name:'" + name + '\'' +
                ", content:" + content +
                ", type:" + type +
                '}';
    }
*/
    @Override
    public int getSize() {
        int size=0;
        for (String fs: content.keySet()
             ) {
            size+= content.get(fs).getSize();
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

    public void addF(IFSEntity fs){
        content.put(name.getName(), fs);
    }


    @Override
    public IFSEntity getElement(String[] path, int index) {
        IFSEntity entity = this.content.get(path[index]);
        if(entity != null) {
            if(index == path.length - 1 ) {
                return entity;
            } else if(entity.getType().equals(FILE_TYPE.FOLDER)){
                return ((IFolder)entity).getElement(path, ++index);
            }
        }
        return null;
    }

    @Override
    public boolean addElement(IFSEntity entity) {
        if(content.put(entity.getName(),entity)!=null){
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean removeElement(String[] path, int index) {
        IFSEntity entity = this.content.get(path[index]);
        if(entity != null) {
            if(index == path.length - 1 ) {
                if (this.content.remove(path[index])!=null) return true;
            } else if(entity.getType().equals(FILE_TYPE.FOLDER)){
                return ((IFolder)entity).removeElement(path, ++index);
            }
        }
        return false;
    }
}
