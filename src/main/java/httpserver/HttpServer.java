package httpserver;

import facade.IFileSystem;
import httpserver.config.Configuration;
import httpserver.config.ConfigurationManager;

public class HttpServer {
    static ConfigurationManager configManager = ConfigurationManager.getInstance();
    static IFileSystem root;

    public static void main(String[] args) {
        configManager.loadConfigFile();
        Configuration config = configManager.getConfiguration();
        String path="/folder1/folder2/file1";
        System.out.println(path.split("/").length);

    }
}
