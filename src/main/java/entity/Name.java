package entity;

public class Name {
    private String name;

    public Name(String name) throws IllegalArgumentException {
        if (!name.matches("^[A-Za-z0-9_.]{1,20}$"))
            throw new IllegalArgumentException("Invalid name");
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
