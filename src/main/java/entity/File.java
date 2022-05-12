package entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class File implements IFile {
    private Name name;
    private byte[] content;
    private FILE_TYPE type;

    public File(String name, byte[] content) throws IllegalArgumentException {
        this.name = new Name(name);
        this.content = content;
        type = FILE_TYPE.FILE;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    @Override
    public byte[] getContent() {
        return content;
    }

    @Override
    public int getSize() {
        if (content == null) {
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

