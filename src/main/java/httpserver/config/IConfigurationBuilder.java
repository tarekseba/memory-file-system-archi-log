package httpserver.config;

public interface IConfigurationBuilder {
    void startBuild();

    public void setPort(String port) throws NumberFormatException;

    public void setContext(String context);

    Configuration getResult();
}
