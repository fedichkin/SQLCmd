package config;

import org.dbunit.DBTestCase;
import org.dbunit.dataset.IDataSet;

/**
 * Created by Денис on 31.07.2016.
 *
 * Конфигурационный файл для dbunit тестов
 */
public class DBUnitConfig extends DBTestCase {

    @Override
    protected IDataSet getDataSet() throws Exception {
        return null;
    }
}
