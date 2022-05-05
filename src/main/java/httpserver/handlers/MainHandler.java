package httpserver.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import entity.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainHandler implements HttpHandler {
    Root root;
    public MainHandler() {
        root=Root.getInstance();
        Folder folder=new Folder("folder1",new HashMap<>());
        folder.addElement(new File("file5","file5".getBytes()));
        Folder folder2=new Folder("folder2",new HashMap<>());
        folder2.addElement(new File("file7",null));
        folder.addElement(new File("file5",null));
        folder.addElement(new File("file6",null));
        folder.addElement(folder2);
        root.addElement(new File("file1",null));
        root.addElement(new File("file2",null));
        root.addElement(new File("file3",null));
        root.addElement(new File("file4",null));
        root.addElement(folder);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String methodeRequest= exchange.getRequestMethod();

        Headers responseHeader=exchange.getResponseHeaders();
        responseHeader.set("Content-Type", "application/json");
        responseHeader.set("Access-Control-Allow-Origin", "*");
        responseHeader.add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        responseHeader.add("Access-Control-Allow-Headers", "Content-Type,Authorization");
        exchange.sendResponseHeaders(200,0);

        ObjectMapper mapper=new ObjectMapper();


        if(methodeRequest.equalsIgnoreCase("GET")){

            OutputStream reponse = exchange.getResponseBody();
            if(exchange.getRequestURI().toString().equals("/")){

                try {
                    List<IFSEntity> list=new ArrayList<>(root.getContent().values());
                    String jsonString = mapper.writeValueAsString(list);
                    StringBuilder stringBuilder=new StringBuilder(jsonString);
                    stringBuilder.insert(0, "{\"result\": ");
                    stringBuilder.insert(stringBuilder.length(), "}");
                    reponse.write(stringBuilder.toString().getBytes());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                String[] path=exchange.getRequestURI().toString().split("/");
                try {
                    String jsonString="";
                    IFSEntity entity=root.getElement(path,1);
                    if(entity.getType().toString().equals("FILE")){
                        reponse.write(((File)entity).getContent());
                    }
                    else if (entity.getType().toString().equals("FOLDER")){
                        List<IFSEntity> list=new ArrayList<>(((Folder)entity).getContent().values());
                        jsonString = mapper.writeValueAsString(list);
                        StringBuilder stringBuilder=new StringBuilder(jsonString);
                        stringBuilder.insert(0, "{\"result\": ");
                        stringBuilder.insert(stringBuilder.length(), "}");
                        reponse.write(stringBuilder.toString().getBytes());
                    }
                    else {

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            reponse.close();
        }
        else if (methodeRequest.equalsIgnoreCase("DELETE")){
            OutputStream reponse = exchange.getResponseBody();
            if(exchange.getRequestURI().toString()!="/"){
                if(root.removeElement(exchange.getRequestURI().toString().split("/"),1)){
                    reponse.write("Suppression effectué".getBytes());
                }
            }
            reponse.close();
        }
        else if (methodeRequest.equalsIgnoreCase("POST")){
            OutputStream reponse = exchange.getResponseBody();
            InputStreamReader isr =  new InputStreamReader(exchange.getRequestBody(),"utf-8");
            BufferedReader br = new BufferedReader(isr);
            // From now on, the right way of moving from bytes to utf-8 characters:
            int b;
            StringBuilder buf = new StringBuilder(512);
            while ((b = br.read()) != -1) {
                buf.append((char) b);
            }
            br.close();
            isr.close();
            reponse.close();
            try {
                ObjectMapper objectMapper=new ObjectMapper();

                Dto dto=objectMapper.readValue(buf.toString(), Dto.class);

                if(dto.getType().toString().equals("FILE")){
                    byte[] tabByte=new byte[dto.getContent().values().size()];
                    int i = 0;
                    for(Byte by : (new ArrayList<>(dto.getContent().values()))){
                        tabByte[i++] = by.byteValue();
                    }
                    if(exchange.getRequestURI().toString().equals("/")){
                        root.addElement(new File(dto.getName(), tabByte));
                    }
                    else {
                        String[] path=exchange.getRequestURI().toString().split("/");
                        IFSEntity entity=root.getElement(path,1);
                        if(entity!=null && entity.getType().toString().equals("FOLDER")){
                            ((Folder)entity).addElement(new File(dto.getName(), tabByte));
                        }
                    }
                } else if (dto.getType().toString().equals("FOLDER")) {
                    if(exchange.getRequestURI().toString().equals("/")){
                        root.addElement(new Folder(dto.getName(),new HashMap<>()));
                    }
                    else {
                        String[] path=exchange.getRequestURI().toString().split("/");
                        IFSEntity entity=root.getElement(path,1);
                        if(entity!=null && entity.getType().toString().equals("FOLDER")){
                            ((Folder)entity).addElement(new Folder(dto.getName(), new HashMap<>()));
                        }
                    }
                }
            }
            catch (Exception exception){
                exception.printStackTrace();
            }
        }
        else {
            exchange.getResponseBody().close();
        }
    }
}
