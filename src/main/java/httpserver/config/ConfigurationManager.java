package httpserver.config;

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

    public void loadConfigFile() {
        System.out.println("hhhh");
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }
}
