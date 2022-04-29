package httpserver.config;

import httpserver.parser.XMLConfigParser;

public class ConfigurationManager {
    private static ConfigurationManager configurationManagerInstance;
    private Configuration configuration;

    private ConfigurationManager() {
    }

    public static ConfigurationManager getInstance() {
        if(configurationManagerInstance == null) {
            configurationManagerInstance = new ConfigurationManager();
        }
        return configurationManagerInstance;
    }

    public void loadConfigFile(String path) {
        IConfigurationBuilder builder = new ConfigurationBuilder();
        XMLConfigParser xmlParser = new XMLConfigParser();
        try {
            xmlParser.load(builder, path);
        } catch(Exception e) {
            e.printStackTrace();
        }
        this.configuration = builder.getResult();
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }
}
