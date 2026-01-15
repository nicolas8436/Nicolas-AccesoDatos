package com.p2hibernate_nicolas;

import java.util.ArrayList;
import org.hibernate.Session;
import config.HibernateUtil;

import entities.Actores;
import entities.Peliculas;
import entities.Directores;
import repositories.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Iniciando sesion a MariaDB");
        Session session = HibernateUtil.get().openSession();
        // AQUI la logica del programa
       
        session.close();
        System.out.println("Cerrando sesion a MariaDB");
    }
}