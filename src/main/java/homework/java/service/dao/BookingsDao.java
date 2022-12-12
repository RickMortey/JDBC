package homework.java.service.dao;

import homework.java.domain.Bookings;
import homework.java.service.db.Jdbc;
import lombok.AllArgsConstructor;

import java.sql.SQLException;

@AllArgsConstructor
public class BookingsDao extends AbstractDao<Bookings> {
    private final Jdbc source;

    @Override
    public void save(Iterable<Bookings> smth) throws SQLException {
        source.preparedStatement("insert into Bookings(book_ref, book_date, total_amount) values (?, ?, ?)",
                insertBookings -> {
                    for (Bookings bookings : smth) {
                        insertBookings.setString(1, bookings.getBookRef());
                        insertBookings.setString(2, bookings.getBookDate());
                        insertBookings.setBigDecimal(3, bookings.getTotalAmount());
                        insertBookings.execute();
                    }
                });
    }
}
