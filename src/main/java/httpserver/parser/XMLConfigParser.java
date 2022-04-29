package httpserver.parser;

import httpserver.config.IConfigurationBuilder;
import httpserver.config.XMLConfigHandler;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

public class XMLConfigParser implements IConfigParser {

    @Override
    public void load(IConfigurationBuilder builder, String file) throws ParserConfigurationException, SAXException {
        builder.startBuild();
        SAXParserFactory spf = SAXParserFactory.newInstance();
        XMLConfigHandler handler = new XMLConfigHandler(builder);
        SAXParser sp = spf.newSAXParser();
        XMLReader xmlReader = sp.getXMLReader();
        xmlReader.setContentHandler(handler);
        xmlReader.setErrorHandler(handler);
        try {
            xmlReader.parse(file);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }
}
