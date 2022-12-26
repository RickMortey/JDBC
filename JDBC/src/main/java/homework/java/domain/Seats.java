package homework.java.domain;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force = true)
public class Seats {
    @CsvBindByPosition(position = 0)
    private final String aircraftCode;
    @CsvBindByPosition(position = 1)
    private final String seatNo;
    @CsvBindByPosition(position = 2)
    private final String fareConditions;
}
