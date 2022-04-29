package httpserver.config;

public interface IConfigurationBuilder {
    void startBuild();
    void setPort(String port);
    void setContext(String context);

    Configuration getResult();
}
