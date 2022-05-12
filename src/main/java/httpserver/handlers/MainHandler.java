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
import java.util.HashMap;
import java.util.List;

public class MainHandler implements HttpHandler {
    Root root;
    AbstractIFSEntityFactory fsEntityFactory;
    IFormatter formatter;

    public MainHandler(AbstractIFSEntityFactory entityFactory, IFormatter formatter) {
        this.formatter = formatter;
        root = Root.getInstance();
        this.fsEntityFactory = entityFactory;
        IFolder folder = entityFactory.createFolder("folder1");
        folder.addElement(entityFactory.createFile("file5", "file5".getBytes()));
        IFolder folder2 = entityFactory.createFolder("folder2");
        folder2.addElement(entityFactory.createFile("file6", "file5".getBytes()));
        folder.addElement(entityFactory.createFile("file7", "file5".getBytes()));
        folder.addElement(entityFactory.createFile("file8", "file5".getBytes()));
        root.addElement(entityFactory.createSymLink("symlink1", "/folder1"));
        folder.addElement(folder2);
        root.addElement(new File("file1", "file1".getBytes()));
        root.addElement(new File("file2", null));
        root.addElement(new File("file3", null));
        root.addElement(new File("file4", null));
        root.addElement(folder);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String methodeRequest = exchange.getRequestMethod();

        Headers responseHeader = exchange.getResponseHeaders();
        responseHeader.set("Content-Type", "application/json");
        responseHeader.set("Access-Control-Allow-Origin", "*");
        responseHeader.add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        responseHeader.add("Access-Control-Allow-Headers", "Content-Type,Authorization");
        //exchange.sendResponseHeaders(200,0);

        ObjectMapper mapper = new ObjectMapper();


        if (methodeRequest.equalsIgnoreCase("GET")) {
            OutputStream reponse = exchange.getResponseBody();
            if (exchange.getRequestURI().toString().equals("/")) {

                try {
                    List<IFSEntity> list = new ArrayList<>(root.getContent().values());
                    exchange.sendResponseHeaders(200, 0);
                    reponse.write(formatter.formatFolder(list));
                } catch (Exception e) {
                    exchange.sendResponseHeaders(500, 0);
                    reponse.write(formatter.formatError("Error while formatting"));
                }
            } else if (exchange.getRequestURI().toString().equals("/favicon.ico")) {
                exchange.sendResponseHeaders(200, 0);
                reponse.close();
            } else {
                String[] path = exchange.getRequestURI().toString().split("/");

                try {
                    String jsonString = "";
                    IFSEntity entity = root.getElement(path, 1);
                    String entityType = entity.getType().toString();
                    if (entityType.equals("FILE")) {
                        exchange.sendResponseHeaders(200, 0);
                        reponse.write(((File) entity).getContent());
                    } else if (entityType.equals("FOLDER")) {
                        List<IFSEntity> list = new ArrayList<>(((Folder) entity).getContent().values());
                        exchange.sendResponseHeaders(200, 0);
                        reponse.write(formatter.formatFolder(list));
                    } else if (entityType.equals("LINK")) {
                        String filePath = ((SymLink) entity).getContent();
                        IFSEntity linkedEntity = root.getElement(filePath.split("/"), 1);
                        if (linkedEntity != null) {
                            switch (linkedEntity.getType().toString()) {
                                case "FILE":
                                    exchange.sendResponseHeaders(200, 0);
                                    reponse.write(((File) linkedEntity).getContent());
                                    break;
                                default:
                                    exchange.sendResponseHeaders(400, 0);
                                    reponse.write(formatter.formatError("Symlink points to a folder"));

                                    break;
                            }
                        } else {
                            exchange.sendResponseHeaders(404, 0);
                            reponse.write(formatter.formatError("Symlink points to wrong path"));
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    exchange.sendResponseHeaders(500, 0);
                    reponse.write(formatter.formatError("ERROR"));
                }
            }
            reponse.close();
        } else if (methodeRequest.equalsIgnoreCase("DELETE")) {
            OutputStream reponse = exchange.getResponseBody();
            if (exchange.getRequestURI().toString() != "/") {
                if (root.removeElement(exchange.getRequestURI().toString().split("/"), 1)) {
                    exchange.sendResponseHeaders(200, 0);
                    reponse.write("Suppression effectué".getBytes());
                }
            }
            reponse.close();
        } else if (methodeRequest.equalsIgnoreCase("POST")) {
            System.out.println("INSIDE");
            OutputStream reponse = exchange.getResponseBody();
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

                Dto dto = objectMapper.readValue(buf.toString(), Dto.class);

                if (dto.getType().toString().equals("FILE")) {
                    byte[] tabByte = new byte[dto.getContent().values().size()];
                    int i = 0;
                    for (Byte by : (new ArrayList<>(dto.getContent().values()))) {
                        tabByte[i++] = by.byteValue();
                    }
                    if (exchange.getRequestURI().toString().equals("/")) {
                        root.addElement(new File(dto.getName(), tabByte));
                    } else {
                        String[] path = exchange.getRequestURI().toString().split("/");
                        IFSEntity entity = root.getElement(path, 1);
                        if (entity != null && entity.getType().toString().equals("FOLDER")) {
                            ((Folder) entity).addElement(new File(dto.getName(), tabByte));
                        }
                    }
                }
                if (dto.getType().toString().equals("LINK")) {
                    System.out.println("LINK SUBMITTED");
                    int i = 0;
                    if (exchange.getRequestURI().toString().equals("/")) {
                        root.addElement(fsEntityFactory.createSymLink(dto.getName(), dto.getPath()));
                    } else {
                        String[] path = exchange.getRequestURI().toString().split("/");
                        IFSEntity entity = root.getElement(path, 1);
                        if (entity != null && entity.getType().toString().equals("FOLDER")) {
                            ((Folder) entity).addElement(fsEntityFactory.createSymLink(dto.getName(), dto.getPath()));
                        }
                    }
                } else if (dto.getType().toString().equals("FOLDER")) {
                    if (exchange.getRequestURI().toString().equals("/")) {
                        root.addElement(new Folder(dto.getName(), new HashMap<>()));
                    } else {
                        String[] path = exchange.getRequestURI().toString().split("/");
                        IFSEntity entity = root.getElement(path, 1);
                        if (entity != null && entity.getType().toString().equals("FOLDER")) {
                            ((Folder) entity).addElement(new Folder(dto.getName(), new HashMap<>()));
                        }
                    }
                }
                exchange.sendResponseHeaders(200, 0);
            } catch (Exception exception) {
                exchange.sendResponseHeaders(500, 0);
                exception.printStackTrace();
            }
            reponse.close();
        } else {
            exchange.sendResponseHeaders(200, 0);
            exchange.getResponseBody().close();
        }
    }
}
