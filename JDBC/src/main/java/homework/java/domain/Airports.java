package homework.java.domain;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

@Data
@NoArgsConstructor(force = true)
public class Airports {
    @CsvBindByPosition(position = 0)
    private final String airportCode;
    @CsvBindByPosition(position = 1)
    private final String airportName;
    @CsvBindByPosition(position = 2)
    private final String city;
    @CsvBindByPosition(position = 3)
    private final String coordinates;
    @CsvBindByPosition(position = 4)
    private final String timezone;
}
