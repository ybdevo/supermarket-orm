package lk.ijse.supermarketfx.dao;

import lk.ijse.supermarketfx.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface CrudDAO<T> extends SuperDAO {
    List<T> getAll() throws SQLException;

    String getLastId() throws SQLException;

    boolean save(T t) throws SQLException;

    boolean update(T t) throws SQLException;

    boolean delete(String id) throws SQLException;

    List<String> getAllIds() throws SQLException;

    Optional<T> findById(String id) throws SQLException;
}
