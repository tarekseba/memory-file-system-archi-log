package httpserver;

import factory.AbstractIFSEntityFactory;

import java.io.IOException;

public interface IFSHttpServer {
    public void createServer(AbstractIFSEntityFactory entityFactory) throws IOException;

    public void startServer();
}
