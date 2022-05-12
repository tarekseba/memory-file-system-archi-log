package httpserver.config;

public class ConfigurationBuilder implements IConfigurationBuilder {
    private int port;
    private String context;

    @Override
    public void startBuild() {
        this.port = 8080;
        this.context = "";
    }

    public void setPort(String port) throws NumberFormatException {
        this.port = Integer.parseInt(port);
    }

    public void setContext(String context) {
        this.context = context;
    }

    @Override
    public Configuration getResult() {
        return new Configuration(this.port, this.context);
    }
}