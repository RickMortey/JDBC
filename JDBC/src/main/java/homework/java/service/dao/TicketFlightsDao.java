package homework.java.service.dao;

import homework.java.domain.Seats;
import homework.java.domain.TicketFlights;
import homework.java.service.db.Jdbc;
import lombok.AllArgsConstructor;

import java.sql.SQLException;

@AllArgsConstructor
public class TicketFlightsDao extends AbstractDao<TicketFlights> {
    private final Jdbc source;

    @Override
    public void save(Iterable<TicketFlights> smth) throws SQLException {
        source.preparedStatement("insert into Ticket_flights(ticket_no, flight_id, fare_conditions, amount) values (?, ?, ?, ?)",
                insertTicketFlights -> {
                    for (TicketFlights ticketFlights : smth) {
                        insertTicketFlights.setString(1, ticketFlights.getTicketNo());
                        insertTicketFlights.setInt(2, ticketFlights.getFlightId());
                        insertTicketFlights.setString(3, ticketFlights.getFareConditions());
                        insertTicketFlights.setBigDecimal(4, ticketFlights.getAmount());
                        insertTicketFlights.execute();
                    }
                });
    }
}
