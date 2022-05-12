package httpserver.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import entity.*;
import factory.AbstractIFSEntityFactory;
import httpserver.formatter.IFormatter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class RequestHandler implements HttpHandler {
    Root root;
    AbstractIFSEntityFactory fsEntityFactory;
    IFormatter formatter;

    public RequestHandler(AbstractIFSEntityFactory entityFactory, IFormatter formatter) throws IOException {
        this.formatter = formatter;
        root = Root.getInstance();
        this.fsEntityFactory = entityFactory;
        IFolder folder = entityFactory.createFolder("folder1");
        folder.addFile(entityFactory.createFile("file5", "file5".getBytes()));
        IFolder folder2 = entityFactory.createFolder("folder2");
        folder2.addFile(entityFactory.createFile("file6", "file5".getBytes()));
        folder.addFile(entityFactory.createFile("file7", "file5".getBytes()));
        folder.addFile(entityFactory.createFile("file8", "file5".getBytes()));
        root.addSymLink(entityFactory.createSymLink("symlink1", "/file1"));
        folder.addFolder(folder2);
        root.addFile(this.fsEntityFactory.createFile("file1", "file1".getBytes()));
        root.addFile(this.fsEntityFactory.createFile("file2", "file2".getBytes()));
        root.addFile(this.fsEntityFactory.createFile("file3", "file3".getBytes()));
        root.addFile(this.fsEntityFactory.createFile("file4", "file4".getBytes()));
        root.addFolder(folder);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String methodeRequest = exchange.getRequestMethod();

        Headers responseHeader = exchange.getResponseHeaders();
        responseHeader.set("Content-Type", "application/json");
        responseHeader.set("Access-Control-Allow-Origin", "*");
        responseHeader.add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        responseHeader.add("Access-Control-Allow-Headers", "Content-Type,Authorization");

        if (methodeRequest.equalsIgnoreCase("GET")) {
            OutputStream response = exchange.getResponseBody();
            if (exchange.getRequestURI().toString().equals("/")) {

                try {
                    List<IFolder> list = new ArrayList<>(root.getFolders().values());
                    List<IFile> filesList = new ArrayList<>(root.getFiles().values());
                    List<ISymLink> links = new ArrayList<>(root.getSymLinks().values());
                    exchange.sendResponseHeaders(200, 0);
                    response.write(formatter.formatFolder(list, filesList, links));
                } catch (Exception e) {
                    exchange.sendResponseHeaders(500, 0);
                    response.write(formatter.formatError("Error while formatting"));
                }
            } else if (exchange.getRequestURI().toString().equals("/favicon.ico")) {
                exchange.sendResponseHeaders(200, 0);
                response.close();
            } else {
                String[] path = exchange.getRequestURI().toString().split("/");

                try {
                    IFile file = root.getFile(path, 1);
                    IFolder folder = root.getFolder(path, 1);
                    ISymLink link = root.getSymLink(path, 1);
                    if (file != null) {
                        exchange.sendResponseHeaders(200, 0);
                        response.write(file.getContent());
                    } else if (folder != null) {
                        List<IFolder> folders = new ArrayList<IFolder>(folder.getFolders().values());
                        List<IFile> files = new ArrayList<IFile>(folder.getFiles().values());
                        List<ISymLink> links = new ArrayList<>(folder.getSymLinks().values());
                        exchange.sendResponseHeaders(200, 0);
                        response.write(formatter.formatFolder(folders, files, links));
                    } else if (link != null) {
                        String filePath = link.getPath();
                        IFile linkedEntity = root.getFile(filePath.split("/"), 1);
                        if (linkedEntity != null) {
                            switch (linkedEntity.getType().toString()) {
                                case "FILE":
                                    exchange.sendResponseHeaders(200, 0);
                                    response.write(linkedEntity.getContent());
                                    break;
                                default:
                                    exchange.sendResponseHeaders(400, 0);
                                    response.write(formatter.formatError("Symlink points to a folder"));

                                    break;
                            }
                        } else {
                            exchange.sendResponseHeaders(404, 0);
                            response.write(formatter.formatError("Symlink points to wrong path"));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    exchange.sendResponseHeaders(500, 0);
                    response.write(formatter.formatError("ERROR"));
                }
            }
            response.close();
        } else if (methodeRequest.equalsIgnoreCase("DELETE")) {
            System.out.println("DELETE");
            OutputStream response = exchange.getResponseBody();
            if (exchange.getRequestURI().toString() != "/") {
                if (root.removeElement(exchange.getRequestURI().toString().split("/"), 1)) {
                    exchange.sendResponseHeaders(200, 0);
                    response.write("Suppression effectué".getBytes());
                }
            }
            response.close();
        } else if (methodeRequest.equalsIgnoreCase("POST")) {
            OutputStream response = exchange.getResponseBody();
            InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            int b;
            StringBuilder buf = new StringBuilder(512);
            while ((b = br.read()) != -1) {
                buf.append((char) b);
            }
            br.close();
            isr.close();
            try {
                ObjectMapper objectMapper = new ObjectMapper();

                EntityDTO entityDTO = objectMapper.readValue(buf.toString(), EntityDTO.class);

                if (entityDTO.getType().toString().equals("FILE")) {
                    byte[] tabByte = new byte[entityDTO.getContent().values().size()];
                    int i = 0;
                    for (Byte by : (new ArrayList<>(entityDTO.getContent().values()))) {
                        tabByte[i++] = by.byteValue();
                    }
                    if (exchange.getRequestURI().toString().equals("/")) {
                        root.addFile(this.fsEntityFactory.createFile(entityDTO.getName(), tabByte));
                    } else {
                        String[] path = exchange.getRequestURI().toString().split("/");
                        IFolder entity = root.getFolder(path, 1);
                        if (entity != null) {
                            entity.addFile(this.fsEntityFactory.createFile(entityDTO.getName(), tabByte));
                        }
                    }
                }
                if (entityDTO.getType().toString().equals("LINK")) {
                    System.out.println("LINK SUBMITTED");
                    int i = 0;
                    if (exchange.getRequestURI().toString().equals("/")) {
                        root.addSymLink(this.fsEntityFactory.createSymLink(entityDTO.getName(), entityDTO.getPath()));
                    } else {
                        String[] path = exchange.getRequestURI().toString().split("/");
                        IFolder entity = root.getFolder(path, 1);
                        if (entity != null) {
                            entity.addSymLink(fsEntityFactory.createSymLink(entityDTO.getName(), entityDTO.getPath()));
                        }
                    }
                } else if (entityDTO.getType().toString().equals("FOLDER")) {
                    if (exchange.getRequestURI().toString().equals("/")) {
                        root.addFolder(this.fsEntityFactory.createFolder(entityDTO.getName()));
                    } else {
                        String[] path = exchange.getRequestURI().toString().split("/");
                        IFolder entity = root.getFolder(path, 1);
                        if (entity != null) {
                            entity.addFolder(this.fsEntityFactory.createFolder(entityDTO.getName()));
                        }
                    }
                }
                exchange.sendResponseHeaders(200, 0);
            } catch (Exception exception) {
                exchange.sendResponseHeaders(500, 0);
                exception.printStackTrace();
            }
            response.close();
        } else {
            exchange.sendResponseHeaders(200, 0);
            exchange.getResponseBody().close();
        }
    }
}
