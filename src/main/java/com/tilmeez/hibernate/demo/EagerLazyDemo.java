package com.tilmeez.hibernate.demo;

import com.tilmeez.hibernate.demo.entity.Course;
import com.tilmeez.hibernate.demo.entity.Instructor;
import com.tilmeez.hibernate.demo.entity.InstructorDetail;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class    EagerLazyDemo {

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

            // get the instructor from db
            int theId = 5;
            Instructor tempInstructor = session.get(Instructor.class, theId);

            System.out.println("Tilmeez: Instructor: " + tempInstructor);

            // get course for the instructor, courses is loaded while session was opne
            System.out.println("Tilmeez: Courses: " + tempInstructor.getCourses());

            // commit transaction
            session.getTransaction().commit();

            // close the session
            session.close();

            // since courses are lazy loaded ... this should fail, if it's not loaded when session was open

            // get course for the instructor
            System.out.println("Tilmeez: Courses: " + tempInstructor.getCourses());

            System.out.println("Tilmeez: Done!");

        }finally {
            // add clean uo code
            session.close();

            factory.close();
        }
    }
}
