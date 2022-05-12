package httpserver;

import com.sun.net.httpserver.HttpHandler;
import factory.AbstractIFSEntityFactory;
import httpserver.config.ConfigurationManager;
import httpserver.formatter.IFormatter;
import httpserver.formatter.JSONFormatter;
import httpserver.handlers.RequestHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class FSHttpServer implements IFSHttpServer {
    com.sun.net.httpserver.HttpServer httpServer;
    ConfigurationManager configManager = ConfigurationManager.getInstance();

    public void createServer(AbstractIFSEntityFactory entityFactory) throws IOException {
        IFormatter formatter = new JSONFormatter();
        HttpHandler handler = new RequestHandler(entityFactory, formatter);
        try {
            this.configManager.loadConfigFile("src/main/resources/XML/config.xml");
            this.httpServer = com.sun.net.httpserver.HttpServer.create(new InetSocketAddress(configManager.getConfiguration().getPort()), 0);
            this.httpServer.createContext("/", handler);
            this.httpServer.setExecutor(Executors.newCachedThreadPool());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startServer() {
        this.httpServer.start();
        System.out.println("Le serveur en ecoute sur le port: " + this.configManager.getConfiguration().getPort());
    }
}
