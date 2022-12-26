package homework.java.service.db;

import homework.java.Main;
import lombok.AllArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.stream.Collectors;

/**
 * Initializes database
 */
@AllArgsConstructor
public class DbInit {
    private final Jdbc source;

    private String getSQL() throws IOException {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        Main.class.getResourceAsStream("dbcreate.sql"),
                        StandardCharsets.UTF_8))) {
            return br.lines().collect(Collectors.joining("\n"));
        }
    }

    public void create() throws SQLException, IOException {
        String sql = getSQL();
        source.statement(stmt -> {
            stmt.execute(sql);
        });
    }
}