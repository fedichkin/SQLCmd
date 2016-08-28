package config;

import java.io.IOException;
import java.util.Properties;

public class JDBCProperties {

    private Properties properties;

    public JDBCProperties(String nameFile) throws IOException {

        properties = new Properties();
        properties.load(Thread.currentThread()
                .getContextClassLoader().getResourceAsStream(nameFile));
    }

    public Properties getProperties() {
        return properties;
    }
}
