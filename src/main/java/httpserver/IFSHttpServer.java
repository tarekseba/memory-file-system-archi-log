package httpserver;

import factory.AbstractIFSEntityFactory;

public interface IFSHttpServer {
    public void createServer(AbstractIFSEntityFactory entityFactory);

    public void startServer();
}
