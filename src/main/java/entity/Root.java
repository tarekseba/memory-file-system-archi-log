package entity;

import java.util.Map;

public class Root implements IFolder{
    private static final String NAME  = "/";
    private static Root instance;
    private Map<String,IFSEntity> content;

    private Root(){}

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

    /*public IFSEntity getElement(String[] path, int index) {
        IFolder entity = this.content.get(path[index]);
        if(entity != null) {
            if(index == path.length - 1 ) {
                return this.content.get(path[index]);
            } else if(entity.getType().equals(FILE_TYPE.FOLDER)){
                return entity.getElement(path, index++);
            }
        }
        return null;
    }*/
}
