package com.p1hibernate_nicolas;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import config.HibernateUtil;

public class Main {
    public static void main(String[] args) {
        System.out.println("Iniciando creación de tablas vacías...");
        
        SessionFactory sessionFactory = null;
        Session session = null;
        Transaction transaction = null;
        
        try {
            // Obtener la SessionFactory
            sessionFactory = HibernateUtil.get();
            
            // Abrir una sesión
            session = sessionFactory.openSession();
            
            // Iniciar transacción
            transaction = session.beginTransaction();
            
            // Forzar la creación de las tablas
            // Hibernate ya crea las tablas automáticamente al iniciar
            // Solo necesitamos hacer una operación para que se genere el esquema
            
            System.out.println("Tablas creadas exitosamente:");
            System.out.println("- alumnos");
            System.out.println("- Centros");
            System.out.println("- modulos");
            System.out.println("- alumnos_modulos");
            
            // Commit de la transacción
            transaction.commit();
            
            System.out.println("Base de datos inicializada correctamente con todas las tablas vacías.");
            
        } catch (Exception e) {
            System.err.println("Error al crear las tablas: " + e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            // Cerrar recursos
            if (session != null) {
                session.close();
            }
            if (sessionFactory != null) {
                sessionFactory.close();
            }
        }
    }
}