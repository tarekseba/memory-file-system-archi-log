import factory.AbstractIFSEntityFactory;
import factory.DefaultIFSEntityFactory;
import httpserver.FSHttpServer;
import httpserver.IFSHttpServer;

public class Client {
    public static void main(String[] args) {
        IFSHttpServer httpServer = new FSHttpServer();
        AbstractIFSEntityFactory entityFactory = new DefaultIFSEntityFactory();
        try {
            httpServer.createServer(entityFactory);
            httpServer.startServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
