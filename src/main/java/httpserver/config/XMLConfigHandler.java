package httpserver.config;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.util.ArrayList;
import java.util.List;

public class XMLConfigHandler extends DefaultHandler implements ContentHandler{
    IConfigurationBuilder configBuilder;
    List<Element> elements;

    public XMLConfigHandler(IConfigurationBuilder builder) {
        elements = new ArrayList<Element>();
        this.configBuilder = builder;
    }

    @Override
    public void startDocument() throws SAXException {
        elements.clear();
    }

    @Override
    public void endDocument() throws SAXException {
        elements.clear();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {

        elements.add(new Element(qName.trim(), atts));
        String val;
        switch(qName.trim()){
            case "port":
                val = atts.getValue("value");
                this.configBuilder.setPort(val);
                break;
            case "context":
                val = atts.getValue("value");
                this.configBuilder.setContext(val);
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        elements.remove(elements.size() - 1);
    }
}