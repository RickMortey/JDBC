package homework.java.service.dao;

import homework.java.domain.Bookings;
import homework.java.domain.Seats;
import homework.java.service.db.Jdbc;
import lombok.AllArgsConstructor;

import java.sql.SQLException;

@AllArgsConstructor
public class SeatsDao extends AbstractDao<Seats> {
    private final Jdbc source;

    @Override
    public void save(Iterable<Seats> smth) throws SQLException {
        source.preparedStatement("insert into Seats(aircraft_code, seat_no, fare_conditions) values (?, ?, ?)",
                insertSeats -> {
                    for (Seats seats : smth) {
                        insertSeats.setString(1, seats.getAircraftCode());
                        insertSeats.setString(2, seats.getSeatNo());
                        insertSeats.setString(3, seats.getFareConditions());
                        insertSeats.execute();
                    }
                });
    }
}
