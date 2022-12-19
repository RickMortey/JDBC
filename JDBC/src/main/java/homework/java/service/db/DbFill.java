package homework.java.service.db;

import homework.java.domain.*;
import homework.java.service.dao.*;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Fills database
 */
@AllArgsConstructor
public class DbFill {
    private final Jdbc source;

    public void fill() throws Exception {
        String baseCsv = "./src/main/resources/homework/java/database/";
        new CsvToDbBuilder<Aircrafts, AircraftsDao>()
                .usingDataType(Aircrafts.class).usingDaoType(AircraftsDao.class)
                .build(baseCsv + "aircrafts_data.csv", source);
        new CsvToDbBuilder<Airports, AirportsDao>()
                .usingDataType(Airports.class)
                .usingDaoType(AirportsDao.class)
                .build(baseCsv + "airports_data.csv", source);
        new CsvToDbBuilder<BoardingPasses, BoardingPassesDao>()
                .usingDataType(BoardingPasses.class)
                .usingDaoType(BoardingPassesDao.class)
                .build(baseCsv + "boarding_passes.csv", source);
        new CsvToDbBuilder<Bookings, BookingsDao>()
                .usingDataType(Bookings.class)
                .usingDaoType(BookingsDao.class)
                .build(baseCsv + "bookings.csv", source);
        new CsvToDbBuilder<Flights, FlightsDao>()
                .usingDataType(Flights.class)
                .usingDaoType(FlightsDao.class)
                .build(baseCsv + "flights.csv", source);
        new CsvToDbBuilder<Seats, SeatsDao>()
                .usingDataType(Seats.class)
                .usingDaoType(SeatsDao.class)
                .build(baseCsv + "seats.csv", source);
        new CsvToDbBuilder<TicketFlights,TicketFlightsDao>()
                .usingDataType(TicketFlights.class)
                .usingDaoType(TicketFlightsDao.class)
                .build(baseCsv + "ticket_flights.csv", source);
        new CsvToDbBuilder<Tickets, TicketsDao>()
                .usingDataType(Tickets.class)
                .usingDaoType(TicketsDao.class)
                .build(baseCsv + "tickets.csv", source);
    }
}
