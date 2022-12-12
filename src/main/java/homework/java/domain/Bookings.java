package homework.java.domain;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@NoArgsConstructor(force = true)
public class Bookings {
    @CsvBindByPosition(position = 0)
    private final String bookRef;
    @CsvBindByPosition(position = 1)
    private final String bookDate;
    @CsvBindByPosition(position = 2)
    private final BigDecimal totalAmount;
}
