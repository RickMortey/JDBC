package homework.java.domain;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor(force = true)
public class Flights {
    @CsvBindByPosition(position = 0)
    private final int flightId;
    @CsvBindByPosition(position = 1)
    private final String flightNo;
    @CsvBindByPosition(position = 2)
    @CsvDate("yyyy-MM-dd HH:mm:ss")
    private final Timestamp scheduledDeparture;
    @CsvBindByPosition(position = 3)
    @CsvDate("yyyy-MM-dd HH:mm:ss")
    private final Timestamp scheduledArrival;
    @CsvBindByPosition(position = 4)
    private final String departureAirport;
    @CsvBindByPosition(position = 5)
    private final String arrivalAirport;
    @CsvBindByPosition(position = 6)
    private final String status;
    @CsvBindByPosition(position = 7)
    private final String aircraftCode;
    @CsvBindByPosition(position = 8)
    @CsvDate("yyyy-MM-dd HH:mm:ss")
    private final Timestamp actualDeparture;
    @CsvBindByPosition(position = 9)
    @CsvDate("yyyy-MM-dd HH:mm:ss")
    private final Timestamp actualArrival;
}
