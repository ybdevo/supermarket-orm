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
        Query<Customer> query = session.createQuery("from Customer", Customer.class);
        session.close();
        return query.list();
    }

    @Override
    public String getLastId() throws SQLException {
        Session session = factoryConfiguration.getSession();
        String lastId = session.createQuery("SELECT c.id FROM Customer c ORDER BY c.id DESC", String.class).setMaxResults(1).uniqueResult();
        session.close();
        return lastId == null ? "C001" : lastId;
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
        ResultSet resultSet = SQLUtil.execute(
                "SELECT * FROM customer WHERE customer_id LIKE ? OR name LIKE ? OR nic LIKE ? OR email LIKE ? OR phone LIKE ?",
                searchText, searchText, searchText, searchText, searchText
        );

        Session session = factoryConfiguration.getSession();
        Query <Customer> query = session.createQuery("FROM Customer WHERE id LIKE ? OR name LIKE ? OR nic LIKE ? OR email LIKE ? OR phone LIKE ?", Customer.class);
        query.setParameter(1, "%something%");
        query.setParameter(2, "%something%");
        query.setParameter(3, "%something%");
        query.setParameter(4, "%something%");
        query.setParameter(5, "%something%");
        session.close();
        return query.list();
    }

    @Override
    public Optional<Customer> findCustomerByNic(String nic) throws SQLException {
        Session session = factoryConfiguration.getSession();
        Query<Customer> query = session.createQuery("from Customer where nic = ?1", Customer.class);
        query.setParameter(1, nic);
        session.close();
        List<Customer> list = query.list();
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
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
