package lk.ijse.supermarketfx.dao.custom.impl;

import lk.ijse.supermarketfx.config.FactoryConfiguration;
import lk.ijse.supermarketfx.dao.SQLUtil;
import lk.ijse.supermarketfx.dao.custom.ItemDAO;
import lk.ijse.supermarketfx.entity.Item;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


public class ItemDAOImpl implements ItemDAO {

    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public List<Item> getAll() {
        return List.of();
    }

    @Override
    public String getLastId() {
        return "";
    }

    @Override
    public boolean save(Item item) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(item);
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
    public boolean update(Item item) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.merge(item);
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
            session.remove(session.get(Item.class, id));
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
        Query<String> query = session.createQuery("SELECT i.id FROM Item i", String.class);
        return query.list();
    }

    @Override
    public Optional<Item> findById(String id) {
        Session session = factoryConfiguration.getSession();
        Item item = session.get(Item.class, id);
        session.close();
        return Optional.of(item);
    }

    @Override
    public boolean reduceQuantity(String id, int qty) throws SQLException {
        Session session = factoryConfiguration.getSession();
        return SQLUtil.execute(
                "update item set quantity = quantity - ? where item_id = ?",
                qty,
                id
        );
    }
}
