package homework.java.domain;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

@Data
@NoArgsConstructor(force = true)
public class Tickets {
    @CsvBindByPosition(position = 0)
    private final String ticketNo;
    @CsvBindByPosition(position = 1)
    private final String bookRef;
    @CsvBindByPosition(position = 2)
    private final String passengerId;
    @CsvBindByPosition(position = 3)
    private final String passengerName;
    @CsvBindByPosition(position = 4)
    private final String contactData;
}
