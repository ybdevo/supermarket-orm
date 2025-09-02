package lk.ijse.supermarketfx.dao.custom.impl;

import lk.ijse.supermarketfx.bo.exception.NotFoundException;
import lk.ijse.supermarketfx.config.FactoryConfiguration;
import lk.ijse.supermarketfx.dao.SQLUtil;
import lk.ijse.supermarketfx.dao.custom.CustomerDAO;
import lk.ijse.supermarketfx.entity.Customer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CustomerDAOImpl implements CustomerDAO {

    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public List<Customer> getAll() throws SQLException {
        Session session = factoryConfiguration.getSession();
        try {
            Query<Customer> query = session.createQuery("from Customer", Customer.class);
            return query.list();
        } finally {
            session.close();
        }
    }

    @Override
    public String getLastId() throws SQLException {
        Session session = factoryConfiguration.getSession();
        try {
            Query<String> query = session.createQuery("SELECT c.id FROM Customer c ORDER BY c.id DESC", String.class).setMaxResults(1);
            return query.list().isEmpty() ? null : query.list().get(0);
        } finally {
            session.close();
        }
    }

    @Override
    public boolean save(Customer customer) throws SQLException {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.persist(customer);
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
    public boolean update(Customer customer) throws SQLException {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.merge(customer);
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
    public boolean delete(String id) throws SQLException {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Customer customer = session.get(Customer.class, id);
            if (customer != null) {
                throw new NotFoundException("Customer not found..!");
            }
            session.remove(customer);
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
    public List<String> getAllIds() throws SQLException {
        Session session = factoryConfiguration.getSession();
        Query<String> query = session.createQuery("SELECT c.id FROM Customer c", String.class);
        session.close();
        return query.list();
    }

    @Override
    public Optional<Customer> findById(String id) throws SQLException {
        Session session = factoryConfiguration.getSession();
        Customer customer = session.get(Customer.class, id);
        session.close();
        return Optional.of(customer);
    }

    @Override
    public List<Customer> search(String text) throws SQLException {
        String searchText = "%" + text + "%";
        Session session = factoryConfiguration.getSession();
       try {
           Query <Customer> query = session.createQuery("" +
                           "FROM Customer c WHERE c.id OR " +
                           "c.name LIKE :text OR c.nic LIKE :text OR " +
                           "c.email LIKE :text OR c.phone LIKE :text",
                   Customer.class);
           query.setParameter("text", searchText);
           return query.list();
       } finally {
           session.close();
       }
    }

    @Override
    public Optional<Customer> findCustomerByNic(String nic) throws SQLException {
        Session session = factoryConfiguration.getSession();
        Query<Customer> query = session.createQuery("from Customer where nic = :nic", Customer.class);
        query.setParameter("nic", nic);
        session.close();
        return query.list().isEmpty() ? Optional.empty() : Optional.of(query.list().get(0));
    }

    @Override
    public boolean existsCustomerByPhoneNumber(String phoneNumber) throws SQLException {
        Session session = factoryConfiguration.getSession();
        Query<Customer> query = session.createQuery("from Customer where phone = ?", Customer.class);
        query.setParameter(1, phoneNumber);
        session.close();
        return !query.list().isEmpty();
    }
}
