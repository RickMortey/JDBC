package homework.java.service.query;

import homework.java.service.db.Jdbc;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.annotation.Documented;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

//import org.codehaus.jackson.JsonGenerationException;
//import org.codehaus.jackson.map.JsonMappingException;
//import org.codehaus.jackson.map.ObjectMapper;

//import static homework.java.service.query.jsonUtility.*;

@AllArgsConstructor
public class ExecuteQuery {
    private final Jdbc source;

    private String extractRuJSON(String jsonString) {
        String json = jsonString.substring(1, jsonString.length() - 1).replace("\\", "");
        return new JSONObject(json).getString("ru");
    }

    @Data
    @AllArgsConstructor
    public static class Query1Result {
        private final String city;
        private final String airportsList;
    }

    @Data
    @AllArgsConstructor
    public class Query2Result {
        private final String city;
        private final Integer cancelledFlightsCount;
    }

    @Data
    @AllArgsConstructor
    public class Query3Result {
        private final String departureCity;
        private final String arrivalCity;
        private final String avgFlightTime;
    }

    @Data
    @AllArgsConstructor
    public class Query4Result {
        private final String month;
        private final int cancelledFlightsCount;
    }

    @Data
    @AllArgsConstructor
    public class Query5Result {
        private final Day weekDay;
        private final int arrivalFlightCount;
        private final int departureFlightCount;
    }

    @Data
    @AllArgsConstructor
    public class Query6Result {
        private final Date day;
        private final int losses;
    }
    enum Day {

        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY,
        SUNDAY
    }


    public ArrayList<Query1Result> executeQuery1() throws SQLException, IOException {
        return source.statement(stmt -> {
            ArrayList<Query1Result> result = new ArrayList<>();
            ResultSet resultSet = stmt.executeQuery(
                    "select city, LISTAGG(airport_code) from Airports\n" +
                            "group by city \n " +
                            "having count(airport_code) > 1"
            );
            while (resultSet.next()) {
                result.add(
                        new Query1Result(extractRuJSON(resultSet.getString(1)),
                                resultSet.getString(2)));
            }
            return result;
        });
    }

    public ArrayList<Query2Result> executeQuery2() throws SQLException, IOException {
        return source.statement(stmt -> {
            ArrayList<Query2Result> result = new ArrayList<>();
            ResultSet resultSet = stmt.executeQuery(
                    "with tmp as (select \n" +
                            "  Airports.city as city \n" +
                            "  ,count(case Flights.status when 'Cancelled' then 1 else null end) as cancelledFlightsCount\n" +
                            "from Airports\n" +
                            "left join Flights\n" +
                            "on Airports.airport_code = Flights.departure_airport\n" +
                            "group by Airports.city\n) " +
                            "select city, cancelledFlightsCount from tmp order by cancelledFlightsCount desc"
            );
            while (resultSet.next()) {
                result.add(
                        new Query2Result(extractRuJSON(resultSet.getString(1)),
                                resultSet.getInt(2)));
            }
            return result;
        });
    }

    public ArrayList<Query3Result> executeQuery3() throws SQLException, IOException {
        return source.statement(stmt -> {
            ArrayList<Query3Result> result = new ArrayList<>();
            ResultSet resultSet = stmt.executeQuery(
                    "with base_flights as \n" +
                            "(\n" +
                            "  select\n" +
                            "  a_dept.city as departure_city\n" +
                            "  ,a_arr.city as arrival_city\n" +
                            "  ,avg(timestampdiff(second, actual_departure, actual_arrival)) as flight_time\n" +
                            "  from Flights f\n" +
                            "  inner join Airports a_dept\n" +
                            "  on f.departure_airport = a_dept.airport_code\n" +
                            "  inner join Airports a_arr\n" +
                            "  on f.arrival_airport = a_arr.airport_code\n" +
                            "  where 1 = 1\n" +
                            "  and f.actual_arrival is not null\n" +
                            "  group by a_dept.city, a_arr.city\n" +
                            ")\n" +
                            ",base_flights_with_rn as \n" +
                            "(\n" +
                            "  select \n" +
                            "  departure_city\n" +
                            "  ,arrival_city\n" +
                            "  ,flight_time\n" +
                            "  ,row_number() over (partition by departure_city order by flight_time asc) as rn\n" +
                            "  from base_flights\n" +
                            ")\n" +
                            "select \n" +
                            "departure_city\n" +
                            ",arrival_city\n" +
                            ",CONCAT(\n" +
                            "    FLOOR(MOD(flight_time, 3600 * 24) / 3600), ' hours ',\n" +
                            "    FLOOR(MOD(flight_time, 3600) / 60), ' minutes ',\n" +
                            "    MOD(flight_time, 60), ' seconds'\n" +
                            "  ) as flight_time\n" +
                            "from base_flights_with_rn\n" +
                            "where rn = 1\n" +
                            "order by flight_time asc"
            );

            while (resultSet.next()) {
                result.add(
                        new Query3Result(extractRuJSON(resultSet.getString(1)), extractRuJSON(resultSet.getString(2)), resultSet.getString(3))
                );
            }
            return result;
        });
    }

    public ArrayList<Query4Result> executeQuery4() throws SQLException, IOException {
        return source.statement(stmt -> {
            ArrayList<Query4Result> result = new ArrayList<>();
            ResultSet resultSet = stmt.executeQuery(
                    "with tmp as " +
                            "(" +
                            "select month(DATE_TRUNC('MONTH', Flights.scheduled_departure)) as month_num \n" +
                            ",monthname(DATE_TRUNC('MONTH', Flights.scheduled_departure)) as month_name \n" +
                            ",sum(case Flights.status when 'Cancelled' then 1 else 0 end) as cancelledFlightsCount\n" +
                            "from Flights\n" +
                            "group by month(DATE_TRUNC('MONTH', Flights.scheduled_departure)), monthname(DATE_TRUNC('MONTH', Flights.scheduled_departure)) \n" +
                            ")" +
                            "select month_name, cancelledFlightsCount from tmp order by month_num asc"
            );

            while (resultSet.next()) {
                result.add(
                        new Query4Result(resultSet.getString(1), resultSet.getInt(2)));
            }
            return result;
        });
    }

    //    DAY_OF_WEEK
    public ArrayList<Query5Result> executeQuery5() throws SQLException, IOException {
        return source.statement(stmt -> {
            ArrayList<Query5Result> result = new ArrayList<>();
            ResultSet resultSet = stmt.executeQuery(
                    "with departure_cte as \n" +
                            "( \n" +
                            "select DAY_OF_WEEK(Flights.scheduled_departure) as weekday_departure \n" +
                            ",count(Flights.flight_id) as departed_flights \n" +
                            "from Flights \n" +
                            "where 1 = 1 \n" +
                            "and Flights.departure_airport in ('SVO', 'VKO', 'DME') \n " +
                            "and Flights.status = 'Arrived' \n" +
                            "group by DAY_OF_WEEK(Flights.scheduled_departure)" +
                            ") \n" +
                            ",arrival_cte as \n" +
                            "( \n" +
                            "select DAY_OF_WEEK(Flights.scheduled_arrival) as weekday_arrival \n" +
                            ",count(Flights.flight_id) as arrived_flights \n" +
                            "from Flights \n" +
                            "where 1 = 1 \n" +
                            "and Flights.arrival_airport in ('SVO', 'VKO', 'DME') \n " +
                            "and Flights.status = 'Arrived' \n" +
                            "group by DAY_OF_WEEK(Flights.scheduled_arrival) \n" +
                            ") \n" +
                            "select \n" +
                            "departure_cte.weekday_departure \n" +
                            ",arrival_cte.arrived_flights \n" +
                            ",departure_cte.departed_flights \n" +
                            "from departure_cte \n" +
                            "left join arrival_cte \n" +
                            "on departure_cte.weekday_departure = arrival_cte.weekday_arrival \n"
            );

            while (resultSet.next()) {
                result.add(
                        new Query5Result(Day.values()[resultSet.getInt(1) - 1], resultSet.getInt(2), resultSet.getInt(3)));
            }
            return result;
        });
    }

    public void executeQuery6(String model) throws SQLException, IOException {
        source.preparedStatement(
                "update Flights\n" +
                        " set status = 'Cancelled'\n" +
                        " where aircraft_code in (select aircraft_code from Aircrafts where model = ?);\n" +
                        "delete from Tickets where ticket_no in\n" +
                        "(select ticket_no from ticket_flights where flight_id in\n" +
                        "(select flight_id from Flights where status = 'Cancelled'\n" +
                        "and aircraft_code in (select aircraft_code from Aircrafts where model = ?)))",
                insertModel ->
                {
                    insertModel.setString(1, model);
                    insertModel.setString(2, model);
                    insertModel.execute();
                }
        );
    }

//    public ArrayList<Query6Result> executeQuery7(String interval) throws SQLException, IOException {
//        String firstDate = interval.substring(1, interval.indexOf(","));
//        String SecondDate = interval.substring(interval.indexOf(",")+2, interval.length() - 1);
//        source.preparedStatement(
//                ""
//        )
//    }
}
