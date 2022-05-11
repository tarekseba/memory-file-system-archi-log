package entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;


public class Root implements IFolder{
    private static final String NAME  = "/";
    private static Root instance;
    private Map<String,IFSEntity> content;

    private Root(){
        this.content=new HashMap<>();
    }

    public static Root getInstance(){
        if (instance==null){
            instance=new Root();

        }
        return instance;
    }



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
        return NAME;
    }

    @Override
    public FILE_TYPE getType() {
        return FILE_TYPE.FOLDER;
    }

    public Map<String, IFSEntity> getContent() {
        return content;
    }



    /*@Override
    public IFSEntity getElement(String[] path, int i) {
        return null;
    }*/

    public IFSEntity getElement(String[] path, int index) {
        IFSEntity entity = this.content.get(path[index]);
        if(entity != null) {
            if(index == path.length - 1 ) {
                return this.content.get(path[index]);
            } else if(entity.getType().equals(FILE_TYPE.FOLDER)){
                return ((IFolder)entity).getElement(path, ++index);
            }
        }
        return null;
    }

    @Override
    public boolean addElement(IFSEntity entity) {
        if(content.put(entity.getName(), entity)!=null){
            return true;
        }
        return false;
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
