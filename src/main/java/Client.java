import factory.AbstractIFSEntityFactory;
import factory.DefaultIFSEntityFactory;
import httpserver.HttpServer;
import httpserver.IHttpServer;

public class Client {
    public static void main(String[] args) {
        IHttpServer httpServer = new HttpServer();
        AbstractIFSEntityFactory entityFactory = new DefaultIFSEntityFactory();
        try {
            httpServer.createServer(entityFactory);
            httpServer.startServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
