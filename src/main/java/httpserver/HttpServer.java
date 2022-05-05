package httpserver;

import com.sun.net.httpserver.HttpExchange;
import entity.File;
import entity.Folder;
import entity.Root;
import facade.IFileSystem;
import httpserver.config.Configuration;
import httpserver.config.ConfigurationBuilder;
import httpserver.config.ConfigurationManager;
import com.sun.net.httpserver.HttpHandler;
import httpserver.config.IConfigurationBuilder;
import httpserver.handlers.MainHandler;
import httpserver.parser.XMLConfigParser;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.concurrent.Executors;

public class HttpServer {
    static com.sun.net.httpserver.HttpServer httpServer;
    static ConfigurationManager configManager = ConfigurationManager.getInstance();
    static IFileSystem root;

    public static void createServer() {
        try {
            configManager.loadConfigFile("src/main/resources/XML/config.xml");
            //Create HttpServer which is listening on the given port
            httpServer = com.sun.net.httpserver.HttpServer.create(new InetSocketAddress(configManager.getConfiguration().getPort()), 0);
            //Create a new context for the given context and handler
            httpServer.createContext("/", new MainHandler());
            //Create a default executor
            httpServer.setExecutor(Executors.newCachedThreadPool());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        /*configManager.loadConfigFile();
        Configuration config = configManager.getConfiguration();
        String path="/folder1/folder2/file1";
        System.out.println(path.split("/").length);*/
        IConfigurationBuilder builder = new ConfigurationBuilder();
        XMLConfigParser xmlParser = new XMLConfigParser();
        try {
            xmlParser.load(builder, "src/main/resources/XML/config.xml");
            createServer();
            httpServer.start();
            System.out.println("Le serveur en ecoute sur le port: "+builder.getResult().getPort());
        } catch (Exception e) {
            System.out.println("INSIDE EXCEPTION");
            e.printStackTrace();
        }
    }
}
