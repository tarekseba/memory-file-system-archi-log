package httpserver.config;

import httpserver.parser.IConfigParser;
import httpserver.parser.XMLConfigParser;

public class ConfigurationManager {
    private static ConfigurationManager configurationManagerInstance;
    private Configuration configuration;

    private ConfigurationManager() {
    }

    public static ConfigurationManager getInstance() {
        if (configurationManagerInstance == null) {
            configurationManagerInstance = new ConfigurationManager();
        }
        return configurationManagerInstance;
    }

    public void loadConfigFile(String path) {
        IConfigurationBuilder builder = new ConfigurationBuilder();
        IConfigParser parser = new XMLConfigParser();
        try {
            parser.load(builder, path);
            this.configuration = builder.getResult();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error occurred during parsing, launching server on default port 8080");
            this.configuration = new Configuration(8080, "");
        }
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }
}
