package homework.java.domain;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force = true)
public class BoardingPasses {
    @CsvBindByPosition(position = 0)
    private final String ticketNo;
    @CsvBindByPosition(position = 1)
    private final int flightId;
    @CsvBindByPosition(position = 2)
    private final int boardingNo;
    @CsvBindByPosition(position = 3)
    private final String seatNo;
}
