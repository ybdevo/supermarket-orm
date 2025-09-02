package lk.ijse.supermarketfx;

import lk.ijse.supermarketfx.config.FactoryConfiguration;
import lk.ijse.supermarketfx.entity.Customer;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.util.List;

public class QueryTest {
    public static void main(String[] args) {
        FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();
        Session session = factoryConfiguration.getSession();

        NativeQuery<Customer> nativeQuery = session.createNativeQuery("select * from customers where name='John Doe'", Customer.class);
        List<Customer> customers = nativeQuery.getResultList();
        customers.forEach(cust -> {
            System.out.println("NativeQuery");
            System.out.println(cust.toString());
        });

        Query<Customer> query = session.createQuery("from Customer where id=:id", Customer.class);
        List<Customer> list = query.list();
        list.forEach(cust -> {
            System.out.println("Query");
            System.out.println(cust.toString());
        });
    }
}
