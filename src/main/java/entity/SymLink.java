package entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
public class SymLink implements IFSEntity{
    private String name;
    private String content;
    private FILE_TYPE type;

    public SymLink(String name, String content) {
        this.name = name;
        this.content = content;
        type = FILE_TYPE.LINK;
    }

    @Override
    public String toString() {
        return "SymLink{" +
                "name:'" + name + '\'' +
                ", content:'" + content + '\'' +
                ", type:" + type +
                '}';
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
