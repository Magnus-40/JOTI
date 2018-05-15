/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JOTI;

import org.hibernate.cfg.Configuration;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * A Singleton class for creating or getting the current session
 *
 * @author Andrew Whitelaw
 */
public class HibernateSession {

    static Session session;

    /**
     * Singleton method for creating or returning a Session object
     * @return Returns the session object
     */
    public static Session getSession() {

        if (session == null) {
            Configuration config = new Configuration();
            SessionFactory sf = config.configure().buildSessionFactory();
            session = sf.openSession();
        }
        return session;
    }
}
