package lk.ijse.supermarketfx;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lk.ijse.supermarketfx.config.FactoryConfiguration;
import lk.ijse.supermarketfx.dto.tm.CustomerTM;
import lk.ijse.supermarketfx.entity.Customer;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.util.List;

public class QueryTest {
    public static void main(String[] args) {
        FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();
        Session session = factoryConfiguration.getSession();

//        NativeQuery<Customer> nativeQuery = session.createNativeQuery("select * from customers where name='John Doe'", Customer.class);
//        List<Customer> customers = nativeQuery.getResultList();
//        customers.forEach(cust -> {
//            System.out.println("NativeQuery");
//            System.out.println(cust.toString());
//        });
//
//        Query<Customer> query = session.createQuery("from Customer where id=:id", Customer.class);
//        List<Customer> list = query.list();
//        list.forEach(cust -> {
//            System.out.println("Query");
//            System.out.println(cust.toString());
//        });


        // 1. Create the CriteriaBuilder object
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        // 2. Create CriteriaQuery Object
        CriteriaQuery<Customer> customerQuery = criteriaBuilder.createQuery(Customer.class);
        // 3. Setup root entity
        Root<Customer> customerRoot = customerQuery.from(Customer.class);
        // 4. Adding Conditions (WHERE)
        customerQuery.select(customerRoot).where(criteriaBuilder.equal(customerRoot.get("name"), "John Doe"));
        // 5. Run the query
        Query<Customer> query = session.createQuery(customerQuery);
        List<Customer> customers = query.list();
    }
}
