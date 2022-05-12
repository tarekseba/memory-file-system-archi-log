package entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SymLink implements ISymLink {
    private Name name;
    private String content;
    private FILE_TYPE type;

    public SymLink(String name, String content) throws IllegalArgumentException {
        this.name = new Name(name);
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
        return name.getName();
    }

    @Override
    public FILE_TYPE getType() {
        return this.type;
    }

    @Override
    public String getPath() {
        return content;
    }
}
