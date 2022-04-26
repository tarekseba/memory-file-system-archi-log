package httpserver.config;

public class ConfigurationManager {
    static ConfigurationManager configurationManagerInstance;
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

    }
}
