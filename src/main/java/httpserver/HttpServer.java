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

    public void createServer() {
        try {
            configManager.loadConfigFile("src/main/resources/XML/config.xml");
            //Create HttpServer which is listening on the given port
            httpServer = com.sun.net.httpserver.HttpServer.create(new InetSocketAddress(configManager.getConfiguration().getPort()), 0);
            //Create a new context for the given context and handler
            httpServer.createContext("/" + configManager.getConfiguration().getContext(), new MainHandler());
            //Create a default executor
            httpServer.setExecutor(null);
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

        /*Root root1=Root.getInstance();
        Folder folder=new Folder("folder1",new HashMap<>());
        folder.addElement(new File("file5",null));
        Folder folder2=new Folder("folder2",new HashMap<>());
        folder2.addElement(new File("file7",null));
        folder.addElement(new File("file5",null));
        folder.addElement(new File("file6",null));
        folder.addElement(folder2);
        root1.addElement(new File("file1",null));
        root1.addElement(new File("file2",null));
        root1.addElement(new File("file3",null));
        root1.addElement(new File("file4",null));
        root1.addElement(folder);*/


        /*System.out.println(root1.getElement("/folder1/folder2/file7".split("/"),1).getName());
        root1.removeElement("/folder1/folder2".split("/"),1);
        System.out.println(root1.getElement("/folder1/folder2/file7".split("/"),1));*/
        try {
            xmlParser.load(builder, "src/main/resources/XML/config.xml");
            InetSocketAddress addr = new InetSocketAddress(builder.getResult().getPort());
            com.sun.net.httpserver.HttpServer server = com.sun.net.httpserver.HttpServer.create(addr, 0);

            server.createContext("/", new MainHandler());
            server.setExecutor(Executors.newCachedThreadPool());
            server.start();
            System.out.println("Le serveur en ecoute sur le port: "+addr.getPort());
        } catch (Exception e) {
            System.out.println("INSIDE EXCEPTION");
            e.printStackTrace();
        }
        System.out.println(builder.getResult().getPort() + " | " + builder.getResult().getContext());
    }
}
