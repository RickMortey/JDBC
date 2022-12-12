package homework.java.service.dao;

import homework.java.domain.Airports;
import homework.java.service.db.Jdbc;
import lombok.AllArgsConstructor;
import org.json.JSONObject;

import java.sql.SQLException;

@AllArgsConstructor
public class AirportsDao extends AbstractDao<Airports> {

    private final Jdbc source;

    @Override
    public void save(Iterable<Airports> smth) throws SQLException {
        source.preparedStatement("insert into Airports(airport_code, airport_name, city, coordinates, timezone) " +
                        "values (?, ?, ?, ?, ?)",
                insertAirports -> {
                    for (Airports airports : smth) {
                        insertAirports.setString(1, airports.getAirportCode());
                        insertAirports.setString(2, airports.getAirportName());
                        insertAirports.setString(3, airports.getCity());
                        insertAirports.setString(4, airports.getCoordinates());
                        insertAirports.setString(5, airports.getTimezone());
                        insertAirports.execute();
                    }
                });
    }

}
