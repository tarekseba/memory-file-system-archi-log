package httpserver.config;

import org.xml.sax.Attributes;

public class Element {
    String name;
    Attributes attributes;

    public Element(String name, Attributes attributes) {
        this.name = name;
        this.attributes = attributes;
    }
}