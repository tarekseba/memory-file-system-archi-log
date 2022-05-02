package entity;

import java.util.Arrays;

public class File implements IFSEntity{
    private String name;
    private byte[] content;
    private FILE_TYPE type;

    public File(String name, byte[] content) {
        this.name = name;
        this.content = content;
        type= FILE_TYPE.FILE;
    }

    @Override
    public String toString() {
        return "File={" +
                "name='" + name + '\'' +
                ", content=" + Arrays.toString(content) +
                ", type=" + type +
                '}';
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public byte[] getContent() {
        return content;
    }

    @Override
    public int getSize() {
        return content.length;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public FILE_TYPE getType() {
        return this.type;
    }
}

