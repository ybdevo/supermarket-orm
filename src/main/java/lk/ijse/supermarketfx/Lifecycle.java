package lk.ijse.supermarketfx;

import lk.ijse.supermarketfx.config.FactoryConfiguration;
import lk.ijse.supermarketfx.entity.Customer;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class Lifecycle {
    public static void main(String[] args) {

//        Transient
        Customer customer = new Customer();
        customer.setName("John");
//        no session
//        no db
//        hibernate no track changes

//        Persistent
//        object is now managed by hibernate
//        have session
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.persist(customer);
        transaction.commit();

//        Detached
        session.close();
//        customer -
//        db
        customer.setName("Doe");

        Session session1 = FactoryConfiguration.getInstance().getSession();

//        Persistent
        session1.merge(customer);

        Transaction transaction1 = session1.beginTransaction();

//        Removed
        session1.remove(customer); // rady to delete

        transaction1.commit(); // after commit db remove data

        Customer customer1 = session1.get(Customer.class, 1);


    }
}