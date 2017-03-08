package config.dbunit;

import org.dbunit.DefaultDatabaseTester;
import org.dbunit.DefaultOperationListener;
import org.dbunit.IDatabaseTester;
import org.dbunit.IOperationListener;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * update to jUnit 4
 */
public abstract  class DatabaseTestCase {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseTestCase.class);

    private IDatabaseTester tester;

    private IOperationListener operationListener;

    protected abstract IDatabaseConnection getConnection() throws Exception;

    protected abstract IDataSet getDataSet() throws Exception;

    protected IDatabaseTester newDatabaseTester() throws Exception{
        logger.debug("newDatabaseTester() - start");

        final IDatabaseConnection connection = getConnection();
        getOperationListener().connectionRetrieved(connection);
        return new DefaultDatabaseTester(connection);
    }

    void setUpDatabaseConfig(DatabaseConfig config)
    {
    }

    IDatabaseTester getDatabaseTester() throws Exception {
        if ( this.tester == null ) {
            this.tester = newDatabaseTester();
        }
        return this.tester;
    }

    protected void closeConnection(IDatabaseConnection connection) throws Exception
    {
        logger.debug("closeConnection(connection={}) - start", connection);

        Assert.assertNotNull( "DatabaseTester is not set", getDatabaseTester() );
        getDatabaseTester().closeConnection( connection );
    }

    private DatabaseOperation getSetUpOperation() throws Exception
    {
        return DatabaseOperation.CLEAN_INSERT;
    }

    protected DatabaseOperation getTearDownOperation() throws Exception
    {
        return DatabaseOperation.NONE;
    }

    public void setUp() throws Exception
    {
        logger.debug("setUp() - start");

        final IDatabaseTester databaseTester = getDatabaseTester();
        Assert.assertNotNull( "DatabaseTester is not set", databaseTester );
        databaseTester.setSetUpOperation( getSetUpOperation() );
        databaseTester.setDataSet( getDataSet() );
        databaseTester.setOperationListener(getOperationListener());
        databaseTester.onSetup();
    }

    public void tearDown() throws Exception
    {
        logger.debug("tearDown() - start");

        try {
            final IDatabaseTester databaseTester = getDatabaseTester();
            Assert.assertNotNull( "DatabaseTester is not set", databaseTester );
            databaseTester.setTearDownOperation( getTearDownOperation() );
            databaseTester.setDataSet( getDataSet() );
            databaseTester.setOperationListener(getOperationListener());
            databaseTester.onTearDown();
        } finally {
            tester = null;
        }
    }

    private IOperationListener getOperationListener()
    {
        logger.debug("getOperationListener() - start");
        if(this.operationListener==null){
            this.operationListener = new DefaultOperationListener(){
                public void connectionRetrieved(IDatabaseConnection connection) {
                    super.connectionRetrieved(connection);
                    setUpDatabaseConfig(connection.getConfig());
                }
            };
        }
        return this.operationListener;
    }
}
