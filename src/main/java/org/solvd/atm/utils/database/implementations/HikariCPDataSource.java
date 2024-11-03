package org.solvd.atm.utils.database.implementations;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solvd.atm.utils.database.exceptions.ConnectionException;
import org.solvd.atm.utils.database.interfaces.IConnection;
import java.io.FileReader;
import java.util.Properties;

public class HikariCPDataSource implements IConnection<HikariDataSource> {
    private static final Logger logger = LogManager.getLogger();
    private static HikariCPDataSource hikariInstance;
    private int poolSize;
    private static HikariDataSource dataSource;

    private HikariCPDataSource(){}

    public static HikariCPDataSource getInstance(){
        if(hikariInstance == null){
            hikariInstance = new HikariCPDataSource();
        }
        return hikariInstance;
    }


    @Override
    public HikariDataSource getDataSource() throws ConnectionException {
        if(dataSource == null){
            Properties properties = new Properties();
            try (FileReader input = new FileReader("src/main/resources/env.properties")){
                properties.load(input);
                HikariConfig config = new HikariConfig();
                config.setJdbcUrl(properties.getProperty("db.url"));
                config.setUsername(properties.getProperty("db.username"));
                config.setPassword(properties.getProperty("db.password"));
                config.setMaximumPoolSize(this.poolSize);
                dataSource = new HikariDataSource(config);
                logger.info("Connection Created");
            }catch (Exception e){
                logger.error(e.getMessage());
            }
        }
        return dataSource;
    }


    @Override
    public void setPoolSize(int size) throws ConnectionException {
        this.poolSize = size;
    }
}
