package entity;

public class SymLink implements IFSEntity{
    private String name;
    private String content;
    private FILE_TYPE type;

    public SymLink(String name, String content) {
        this.name = name;
        this.content = content;
        type= FILE_TYPE.LINK;
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public FILE_TYPE getType() {
        return this.type;
    }

    public String getContent() {
        return content;
    }
}
