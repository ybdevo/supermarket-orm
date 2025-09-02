package lk.ijse.supermarketfx.config;

import lk.ijse.supermarketfx.entity.Customer;
import lk.ijse.supermarketfx.entity.Item;
import lk.ijse.supermarketfx.entity.Order;
import lk.ijse.supermarketfx.entity.OrderDetail;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class FactoryConfiguration {
    private static FactoryConfiguration factoryConfiguration;
    private SessionFactory sessionFactory;

    private FactoryConfiguration() {
        Configuration configuration = new Configuration();
        configuration.configure();

        configuration.addAnnotatedClass(Customer.class);
        configuration.addAnnotatedClass(Item.class);
        configuration.addAnnotatedClass(Order.class);
        configuration.addAnnotatedClass(OrderDetail.class);

        sessionFactory = configuration.buildSessionFactory();
    }

    public static FactoryConfiguration getInstance() {
        return factoryConfiguration == null ? factoryConfiguration = new FactoryConfiguration() : factoryConfiguration;
    }

    public Session getSession(){
        Session session = sessionFactory.openSession();
        return session;
    }

    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}