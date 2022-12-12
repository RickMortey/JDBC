package homework.java.domain;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;

import com.opencsv.bean.AbstractCsvConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

import java.util.HashMap;

@Data
@NoArgsConstructor(force = true)
public class Aircrafts {
    @CsvBindByPosition(position = 0)
    private final String aircraftCode;
//    @CsvCustomBindByPosition(position = 1, converter = HasMapConverter) BindByPosition(position = 1)
    @CsvBindByPosition(position = 1)
    private String model;
    @CsvBindByPosition(position = 2)
    private int range;
}