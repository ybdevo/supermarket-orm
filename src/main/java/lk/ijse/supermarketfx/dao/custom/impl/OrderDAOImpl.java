package lk.ijse.supermarketfx.dao.custom.impl;

import lk.ijse.supermarketfx.config.FactoryConfiguration;
import lk.ijse.supermarketfx.dao.custom.OrderDAO;
import lk.ijse.supermarketfx.entity.Order;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


public class OrderDAOImpl implements OrderDAO {
    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();
    @Override
    public List<Order> getAll() throws SQLException {
        Session session = factoryConfiguration.getSession();
        Query<Order> query = session.createQuery("from Order", Order.class);
        session.close();
        return query.list();
    }

    @Override
    public String getLastId() throws SQLException {
        Session session = factoryConfiguration.getSession();
        String lastId = session.createQuery("SELECT o.id FROM Order o ORDER BY o.id DESC", String.class).setMaxResults(1).uniqueResult();
        session.close();
        return lastId == null ? "O001" : lastId;
    }

    @Override
    public boolean save(Order order) throws SQLException {
        Session currentSession = FactoryConfiguration.getInstance().getCurrentSession();
        try {
            currentSession.persist(order);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Order order) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.merge(order);
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
            session.remove(session.get(Order.class, id));
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
        Query<String> query = session.createQuery("SELECT o.id FROM Order AS o", String.class);
        session.close();
        return query.list();
    }

    @Override
    public Optional<Order> findById(String id) {
        Session session = factoryConfiguration.getSession();
        Order order = session.get(Order.class, id);
        session.close();
        return Optional.of(order);
    }

    @Override
    public boolean existOrdersByCustomerId(String customerId) throws SQLException {
        Session session = factoryConfiguration.getSession();
        Query<Order> query = session.createQuery("from Order o where o.customer.id = :customerId");
        query.setParameter("customerId", customerId);
        session.close();
        return !query.list().isEmpty();
    }
}
