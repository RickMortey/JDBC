package homework.java.service.dao;

import homework.java.domain.Aircrafts;
import homework.java.service.db.Jdbc;
import lombok.AllArgsConstructor;

import java.sql.SQLException;

import org.json.JSONObject;

@AllArgsConstructor
public class AircraftsDao extends AbstractDao<Aircrafts> {

    private final Jdbc source;

    @Override
    public void save(Iterable<Aircrafts> smth) throws SQLException {
        source.preparedStatement("insert into Aircrafts(aircraft_code, model, range) values (?, ?, ?)",
                insertAircrafts -> {
                    for (Aircrafts aircrafts : smth) {
                        insertAircrafts.setString(1, aircrafts.getAircraftCode());
                        insertAircrafts.setString(2, aircrafts.getModel());
                        insertAircrafts.setInt(3, aircrafts.getRange());
                        insertAircrafts.execute();
                    }
                });
    }
}
