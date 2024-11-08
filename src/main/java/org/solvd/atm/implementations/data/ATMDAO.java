package org.solvd.atm.implementations.data;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solvd.atm.domain.atm.ATM;
import org.solvd.atm.interfaces.data.IATMDAO;
import org.solvd.atm.utils.database.implementations.HikariCPDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ATMDAO implements IATMDAO {
    private static final Logger logger = LogManager.getLogger(ATMDAO.class);
    HikariCPDataSource dataSource;

    String FIND_ONE_ATM_DIFFERENT = "SELECT serie_number FROM atms WHERE serie_number NOT IN(";

    public ATMDAO(){
        this.dataSource = HikariCPDataSource.getInstance();
    }

    @Override
    public ATM findOneATMSerieWhichIsNotInTheList(List<String> seriesList) {
        String query = String.join(",",seriesList.stream().map(serie->"?").toArray(String[]::new));
        FIND_ONE_ATM_DIFFERENT += query+ ") LIMIT 1";
        try(Connection connection = dataSource.getDataSource().getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_ONE_ATM_DIFFERENT)){
            for(int i = 0; i< seriesList.size();i++){
                statement.setString(i+1, seriesList.get(i));
            }
            try(ResultSet resultSet = statement.executeQuery()){
                if(resultSet.next()){
                    ATM atmFound = new ATM();
                    String serie = resultSet.getString("serie_number");
                    atmFound.setSerieNumber(serie);
                    return atmFound;
                }else{
                    logger.warn("There are not ATMs available, try later");
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
