package httpserver;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import entity.IFolder;
import entity.Root;
import factory.AbstractIFSEntityFactory;
import httpserver.config.ConfigurationManager;
import httpserver.formatter.IFormatter;
import httpserver.formatter.JSONFormatter;
import httpserver.handlers.RequestHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class FSHttpServer implements IFSHttpServer {
    HttpServer httpServer;
    ConfigurationManager configManager = ConfigurationManager.getInstance();
    Root root;

    public void createServer(AbstractIFSEntityFactory entityFactory) throws IOException {
        root = Root.getInstance();
        this.fillUpFilesystem(entityFactory);
        IFormatter formatter = new JSONFormatter();
        HttpHandler handler = new RequestHandler(entityFactory, formatter);
        try {
            this.configManager.loadConfigFile("src/main/resources/XML/config.xml");
            this.httpServer = HttpServer.create(new InetSocketAddress(configManager.getConfiguration().getPort()), 0);
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

    private void fillUpFilesystem(AbstractIFSEntityFactory entityFactory) {
        IFolder folder = entityFactory.createFolder("folder1");
        IFolder folder2 = entityFactory.createFolder("folder2");
        root.addFile(entityFactory.createFile("file1", "file1".getBytes()));
        root.addFile(entityFactory.createFile("file2", "file2".getBytes()));
        root.addFolder(folder);
        root.addSymLink(entityFactory.createSymLink("symlink1", "/file1"));
        folder.addFile(entityFactory.createFile("file3", "file3".getBytes()));
        folder.addFile(entityFactory.createFile("file4", "file4".getBytes()));
        folder.addFolder(folder2);
        folder2.addFile(entityFactory.createFile("file5", "file5".getBytes()));
    }
}
