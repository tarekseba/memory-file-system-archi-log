package httpserver.parser;

import httpserver.config.IConfigurationBuilder;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;

public interface IConfigParser {
    public void load(IConfigurationBuilder builder, String file) throws Exception;

    public boolean validate(File xsdfile, File xmlfile) throws IOException, SAXException;
}

