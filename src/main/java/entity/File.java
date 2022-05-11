package entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Arrays;

@Setter
@Getter
public class File implements IFSEntity{
    private Name name;
    private byte[] content;
    private FILE_TYPE type;

    public File(String name, byte[] content) throws IllegalArgumentException {
        this.name = new Name(name);
        this.content = content;
        type= FILE_TYPE.FILE;
    }

    /*@Override
    public String toString() {
        return "File{" +
                "name='" + name + '\'' +
                ", content:" + Arrays.toString(content) +
                ", type:" + type +
                '}';
    }*/

    public void setContent(byte[] content) {
        this.content = content;
    }

    public byte[] getContent() {
        return content;
    }

    @Override
    public int getSize() {
        if(content==null){
            return 0;
        }
        return content.length;
    }

    @Override
    public String getName() {
        return name.getName();
    }

    @Override
    public FILE_TYPE getType() {
        return this.type;
    }
}

