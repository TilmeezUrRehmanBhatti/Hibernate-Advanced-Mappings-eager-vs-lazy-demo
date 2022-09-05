package com.tilmeez.hibernate.demo;

import com.tilmeez.hibernate.demo.entity.Course;
import com.tilmeez.hibernate.demo.entity.Instructor;
import com.tilmeez.hibernate.demo.entity.InstructorDetail;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;


public class FetchJoinDemo {

    public static void main(String[] args) {

        // create session factory
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Instructor.class)
                .addAnnotatedClass(InstructorDetail.class)
                .addAnnotatedClass(Course.class)
                .buildSessionFactory();

        // create session
        Session session = factory.getCurrentSession();

        try {

            // start a transaction
            session.beginTransaction();

            // option 2: Hibernate query with HQL

            // get the instructor from db
            int theId = 5;

            Query<Instructor> query =
                    session.createQuery("SELECT i FROM Instructor i " +
                            "JOIN FETCH i.courses " +
                            "WHERE i.id=:theInstructorId",
                            Instructor.class);

            // Set parameter on query
            query.setParameter("theInstructorId", theId);

            // execute query and get instructor
            Instructor tempInstructor = query.getSingleResult();

            System.out.println("Tilmeez: Instructor: " + tempInstructor);



            // commit transaction
            session.getTransaction().commit();

            // close the session
            session.close();

            System.out.println("\nThe session is closed now!!");

            // get course for the instructor
            System.out.println("\nTilmeez: Courses: " + tempInstructor.getCourses());

            System.out.println("Tilmeez: Done!");

        }finally {
            // add clean uo code
            session.close();

            factory.close();
        }
    }
}
