package homework.java.service.dao;

import java.sql.SQLException;

public abstract class AbstractDao<T> {
    public abstract void save(Iterable<T> smth) throws SQLException;
}
