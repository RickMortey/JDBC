package homework.java.service.dao;

import homework.java.domain.BoardingPasses;
import homework.java.service.db.Jdbc;
import lombok.AllArgsConstructor;

import java.sql.SQLException;

@AllArgsConstructor
public class BoardingPassesDao extends AbstractDao<BoardingPasses> {
    private final Jdbc source;

    @Override
    public void save(Iterable<BoardingPasses> smth) throws SQLException {
        source.preparedStatement("insert into Boarding_passes(ticket_no, flight_id, boarding_no, seat_no) values (?, ?, ?, ?)",
                insertBoardingPasses -> {
                    for (BoardingPasses boardingPasses : smth) {
                        insertBoardingPasses.setString(1, boardingPasses.getTicketNo());
                        insertBoardingPasses.setInt(2, boardingPasses.getFlightId());
                        insertBoardingPasses.setInt(3, boardingPasses.getBoardingNo());
                        insertBoardingPasses.setString(4, boardingPasses.getSeatNo());
                        insertBoardingPasses.execute();
                    }
                });
    }
}
