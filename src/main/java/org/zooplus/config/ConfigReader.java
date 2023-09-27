package org.zooplus.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static final String PROPERTIES_FILE="zooplus-config.properties";
    private Properties properties;
    private static final Logger LOGGER = LogManager.getLogger(ConfigReader.class);

    public ConfigReader() {
        properties = new Properties();

        // Load the properties file
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            properties.load(input);
        } catch (IOException ex) {
            LOGGER.error("Failed to load properties file: " + PROPERTIES_FILE);
            ex.printStackTrace();
        }
    }

    public String getProperty(String key){
        return properties.getProperty(key);
    }
}
