package entity;

public class File implements IFSEntity{
    private String name;
    private Byte[] content;
    private FILE_TYPE type;

    public File(String name, Byte[] content) {
        this.name = name;
        this.content = content;
        type= FILE_TYPE.FILE;
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

