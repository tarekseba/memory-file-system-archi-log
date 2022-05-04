package httpserver.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import entity.File;
import entity.Folder;
import entity.Root;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;

public class MainHandler implements HttpHandler {
    Root root;
    public MainHandler() {
        root=Root.getInstance();
        Folder folder=new Folder("folder1",new HashMap<>());
        folder.addElement(new File("file5",null));
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
        exchange.sendResponseHeaders(200,0);

        ObjectMapper mapper=new ObjectMapper();


        if(methodeRequest.equalsIgnoreCase("GET")){

            OutputStream reponse = exchange.getResponseBody();

            if(exchange.getRequestURI().toString().equals("/")){

                try {
                    //mapper.writeValue(new java.io.File("root.json"), root.getContent());
                    String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(root.getContent());
                    reponse.write(jsonString.getBytes());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                String[] path=exchange.getRequestURI().toString().split("/");
                try {
                    //mapper.writeValue(new java.io.File("root.json"), root.getContent());
                    String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(root.getElement(path,1));
                    reponse.write(jsonString.getBytes());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            reponse.close();
        }
        else if (methodeRequest.equalsIgnoreCase("DELETE")){

            OutputStream reponse = exchange.getResponseBody();

            if(exchange.getRequestURI().toString()!="/"){
                if(root.removeElement("/folder1/folder2".split("/"),1)){
                    reponse.write("Suppression effectué".getBytes());
                }
            }
            System.out.println("delete");
            reponse.close();
        }
        else if (methodeRequest.equalsIgnoreCase("POST")){
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
            //Folder file=objectMapper.readValue(buf.toString(),Folder.class);

        }
    }
}
