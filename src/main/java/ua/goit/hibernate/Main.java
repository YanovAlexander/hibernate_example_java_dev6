package ua.goit.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import java.util.List;


public class Main {
    public static void main(String[] args) {
        SessionFactory factory;

        try {
            final ServiceRegistry build = new StandardServiceRegistryBuilder()
                    .configure()
                    .build();
            factory = new MetadataSources(build)
                    .buildMetadata()
                    .buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }


        Message message = new Message();
        message.setMessage("Hello hibernate example121");

        try (final Session session = factory.openSession()){
            final Transaction transaction = session.beginTransaction();
            session.persist(message);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        try(final Session session = factory.openSession()) {
            final Transaction transaction = session.beginTransaction();
            final List<Message> messages = session.createQuery("FROM Message").list();
            messages.forEach(System.out::println);
        }
    }
}
