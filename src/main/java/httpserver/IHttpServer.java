package httpserver;

import factory.AbstractIFSEntityFactory;

public interface IHttpServer {
    public void createServer(AbstractIFSEntityFactory entityFactory);
    public void startServer();
}
