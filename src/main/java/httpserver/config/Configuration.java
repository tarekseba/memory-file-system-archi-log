package httpserver.config;

public class Configuration {
    private int port;
    private String context;


    public Configuration(int port, String context) {
        this.port = port;
        this.context = context;
    }

    public int getPort() {
        return port;
    }

    public String getContext() {
        return this.context;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
