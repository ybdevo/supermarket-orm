package lk.ijse.supermarketfx.dao.custom.impl;

import lk.ijse.supermarketfx.config.FactoryConfiguration;
import lk.ijse.supermarketfx.dao.SQLUtil;
import lk.ijse.supermarketfx.dao.custom.OrderDetailsDAO;
import lk.ijse.supermarketfx.entity.OrderDetail;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


public class OrderDetailDAOImpl implements OrderDetailsDAO {

    private FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public List<OrderDetail> getAll() {
        Session session = factoryConfiguration.getSession();
        Query<OrderDetail> query = session.createQuery("from OrderDetail", OrderDetail.class);
        session.close();
        return query.list();
    }

    @Override
    public String getLastId() {
        Session session = factoryConfiguration.getSession();
        String lastId = session.createQuery("SELECT od.id FROM OrderDetail od ORDER BY od.id DESC", String.class).setMaxResults(1).uniqueResult();
        session.close();
        return lastId == null ? "OD001" : lastId;
    }

    @Override
    public boolean save(OrderDetail orderDetail) throws SQLException {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(orderDetail);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean update(OrderDetail orderDetail) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.merge(orderDetail);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean delete(String id) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.remove(session.get(OrderDetail.class, id));
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public List<String> getAllIds() {
        Session session = factoryConfiguration.getSession();
        Query<String> query = session.createQuery("SELECT od.id FROM OrderDetail od", String.class);
        session.close();
        return query.list();
    }

    @Override
    public Optional<OrderDetail> findById(String id) {
        Session session = factoryConfiguration.getSession();
        OrderDetail orderDetail = session.get(OrderDetail.class, id);
        session.close();
        return Optional.of(orderDetail);
    }
}
