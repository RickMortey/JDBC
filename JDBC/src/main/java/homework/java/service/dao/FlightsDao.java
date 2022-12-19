package homework.java.service.dao;

import homework.java.domain.Flights;
import homework.java.service.db.Jdbc;
import lombok.AllArgsConstructor;

import java.sql.SQLException;

@AllArgsConstructor
public class FlightsDao extends AbstractDao<Flights> {
    private final Jdbc source;

    @Override
    public void save(Iterable<Flights> smth) throws SQLException {
        source.preparedStatement("insert into Flights(flight_id, flight_no, scheduled_departure, scheduled_arrival, departure_airport, arrival_airport, status, aircraft_code, actual_departure, actual_arrival) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                insertFlights -> {
                    for (Flights flights : smth) {
                        insertFlights.setInt(1, flights.getFlightId());
                        insertFlights.setString(2, flights.getFlightNo());
                        insertFlights.setTimestamp(3, flights.getScheduledDeparture());
                        insertFlights.setTimestamp(4, flights.getScheduledArrival());
                        insertFlights.setString(5, flights.getDepartureAirport());
                        insertFlights.setString(6, flights.getArrivalAirport());
                        insertFlights.setString(7,  flights.getStatus());
                        insertFlights.setString(8, flights.getAircraftCode());
                        insertFlights.setTimestamp(9, flights.getActualDeparture());
                        insertFlights.setTimestamp(10, flights.getActualArrival());
                        insertFlights.execute();
                    }
                });
    }
}
