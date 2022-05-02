package httpserver.parser;

import httpserver.config.IConfigurationBuilder;
import httpserver.config.XMLConfigHandler;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;

public class XMLConfigParser implements IConfigParser {

    @Override
    public void load(IConfigurationBuilder builder, String file) throws ParserConfigurationException, SAXException, IOException {
        builder.startBuild();
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setValidating(true);
        valid(new File("src/main/resources/XML/config.xsd"),new File("src/main/resources/XML/config.xml"));
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

    public boolean valid(File xsdfile, File xmlfile) throws IOException, SAXException {
        Source xml=new StreamSource(xmlfile);
        SchemaFactory schemaFactory=SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        try {
            Schema schema=schemaFactory.newSchema(xsdfile);
            Validator validator=schema.newValidator();
            validator.validate(xml);
        } catch (IOException|SAXException e) {
            throw e;
        }
        return true;
    }
}
