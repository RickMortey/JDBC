package homework.java.service.dao;

import homework.java.domain.TicketFlights;
import homework.java.domain.Tickets;
import homework.java.service.db.Jdbc;
import lombok.AllArgsConstructor;

import java.sql.SQLException;

@AllArgsConstructor
public class TicketsDao extends AbstractDao<Tickets> {
    private final Jdbc source;

    @Override
    public void save(Iterable<Tickets> smth) throws SQLException {
        source.preparedStatement("insert into Tickets(ticket_no, book_ref, passenger_id, passenger_name, contact_data)" +
                        "values (?, ?, ?, ?, ? FORMAT JSON)",
                insertTickets -> {
                    for (Tickets tickets : smth) {
                        insertTickets.setString(1, tickets.getTicketNo());
                        insertTickets.setString(2, tickets.getBookRef());
                        insertTickets.setString(3, tickets.getPassengerId());
                        insertTickets.setString(4, tickets.getPassengerName());
                        insertTickets.setString(5, tickets.getContactData().toString());
                        insertTickets.execute();
                    }
                });
    }
}
