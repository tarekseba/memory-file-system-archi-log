package httpserver.parser;

import httpserver.config.IConfigurationBuilder;

public interface IConfigParser {
    public void load(IConfigurationBuilder builder, String file) throws Exception;
}
