package lk.ijse.supermarketfx.dao.custom.impl;

import lk.ijse.supermarketfx.dao.SQLUtil;
import lk.ijse.supermarketfx.dao.custom.OrderDAO;
import lk.ijse.supermarketfx.entity.Order;
import lk.ijse.supermarketfx.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class OrderDAOImpl implements OrderDAO {
    @Override
    public List<Order> getAll() throws SQLException {
        List<Order> list = new ArrayList<>();

        ResultSet rs = SQLUtil.execute("SELECT * FROM orders");
        while (rs.next()) {
            list.add(new Order(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getDate(3)
            ));
        }

        return list;
    }

    @Override
    public String getLastId() throws SQLException {
        ResultSet resultSet = CrudUtil.execute(
                "select order_id from orders order by order_id desc limit 1"
        );
        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }

    @Override
    public boolean save(Order order) throws SQLException {
        return SQLUtil.execute("insert into orders values (?,?,?)",
                order.getId(),
                order.getCustomerId(),
                order.getOrderDate()
        );
    }

    @Override
    public boolean update(Order order) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public List<String> getAllIds() {
        return List.of();
    }

    @Override
    public Optional<Order> findById(String id) {
        return Optional.empty();
    }

    @Override
    public boolean existOrdersByCustomerId(String customerId) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM orders WHERE customer_id = ?", customerId);
        return resultSet.next();
    }
}
