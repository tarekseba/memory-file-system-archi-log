package entity;

import java.util.List;
import java.util.Map;

public class Folder implements IFSEntity{
    private  String name;
    private Map<String,IFSEntity> content;
    private FILE_TYPE type;

    public Folder(String name, Map<String,IFSEntity> content) {
        this.name = name;
        this.content = content;
        type= FILE_TYPE.FOLDER;
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
        return name;
    }

    @Override
    public FILE_TYPE getType() {
        return this.type;
    }

    public void addF(IFSEntity fs){
        content.put(name,fs);
    }

}
