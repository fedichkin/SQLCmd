package config.dbunit;

import org.dbunit.IDatabaseTester;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Данный класс позволяет использовать jUnit 4-й версии в библиотеке dbUnit
 * (c)https://gist.github.com/kingargyle/9195975
 */
public abstract  class DBTestCase extends DatabaseTestCase {

    private static final Logger logger = LoggerFactory
            .getLogger(DBTestCase.class);

    protected final IDatabaseConnection getConnection() throws Exception {
        logger.debug("getConnection() - start");

        final IDatabaseTester databaseTester = getDatabaseTester();
        Assert.assertNotNull("DatabaseTester is not set", databaseTester);
        IDatabaseConnection connection = databaseTester.getConnection();
        setUpDatabaseConfig(connection.getConfig());
        return connection;
    }

    protected IDatabaseTester newDatabaseTester() throws Exception {
        return new PropertiesBasedJdbcDatabaseTester();
    }
}
