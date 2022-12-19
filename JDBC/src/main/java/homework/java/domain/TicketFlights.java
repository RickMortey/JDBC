package homework.java.domain;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor(force = true)
public class TicketFlights {
    @CsvBindByPosition(position = 0)
    private final String ticketNo;
    @CsvBindByPosition(position = 1)
    private final int flightId;
    @CsvBindByPosition(position = 2)
    private final String fareConditions;
    @CsvBindByPosition(position = 3)
    private final BigDecimal amount;
}
