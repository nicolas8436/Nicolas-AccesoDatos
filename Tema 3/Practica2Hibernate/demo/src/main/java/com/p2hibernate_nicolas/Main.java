package com.p2hibernate_nicolas;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.Transaction;

import config.HibernateUtil;

import entities.Actores;
import entities.Peliculas;
import entities.Directores;
import repositories.*;

public class Main {
    
    //public static int numPeli = 0;
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        System.out.println("Iniciando sesion de Oracle");
        Session session = HibernateUtil.get().openSession();
        // AQUI la logica del programa
        int eleccion = 0;
        while (eleccion != 5) {
            System.out.println("\nEliga una opcion del menu:");
            System.out.println("1. Peliculas");
            System.out.println("2. Insertar pelicula");
            System.out.println("3. Borrar pelicula por id");
            System.out.println("4. Peliculas por director");
            System.out.println("5. Salir\n");
            try {
                eleccion = scn.nextInt();
            } catch (Exception e) {
                System.out.println("Debes meter un numero entre 1 y 5");
                eleccion = 0;
            }
            scn.nextLine();

            switch (eleccion) {
                case 0:
                    break;
                case 1:
                    mostrarPeli(session);

                    break;

                case 2:
    Directores directorPelicula = null;
    String fechaEstrenoPelicula;
    String fechaNacimientoDirector = null;
    
    System.out.println("\n=== INSERTAR NUEVA PELÍCULA ===");
    
    // 1. Título de la película
    System.out.print("Título de la película: ");
    String titulo = scn.nextLine();
    
    // 2. Fecha de estreno de la película
    while(true){
        System.out.print("Fecha de estreno (DD/MM/AAAA): ");
        fechaEstrenoPelicula = scn.nextLine();
        if(fechaEstrenoPelicula.matches("\\d{2}/\\d{2}/\\d{4}")){
            break;
        } else {
            System.out.println("Formato incorrecto. Use DD/MM/AAAA");
        }
    }
    
    // 3. Nombre del director
    System.out.print("Nombre del director: ");
    String nombreDirector = scn.nextLine();
    
    // 4. Buscar si el director existe
    directorPelicula = director(session, nombreDirector);
    
    // 5. Si NO existe, crear nuevo director
    if(directorPelicula == null){
        System.out.println("\nEl director no existe. Creando nuevo registro...");
        
        // Pedir fecha de nacimiento
        while(true){
            System.out.print("Fecha de nacimiento del director (DD/MM/AAAA): ");
            fechaNacimientoDirector = scn.nextLine();
            if(fechaNacimientoDirector.matches("\\d{2}/\\d{2}/\\d{4}")){
                break;
            } else {
                System.out.println("Formato incorrecto. Use DD/MM/AAAA");
            }
        }
        
        // CREAR NUEVO DIRECTOR
        Transaction txDirector = null;
        try {
            txDirector = session.beginTransaction();
            
            // Crear objeto director
            directorPelicula = new Directores();
            directorPelicula.setNombre(nombreDirector);
            directorPelicula.setFecha_Nac(fechaNacimientoDirector);
            
            // Guardar en base de datos
            session.save(directorPelicula);
            txDirector.commit();
            
            System.out.println("Director creado exitosamente");
            System.out.println("Nombre: " + directorPelicula.getNombre());
            System.out.println("ID: " + directorPelicula.getDirectorId());
            
        } catch (Exception e) {
            if (txDirector != null) txDirector.rollback();
            System.out.println("Error al crear director: " + e.getMessage());
            e.printStackTrace();
            break; // Salir si hay error
        }
    } else {
        System.out.println("Director encontrado en la base de datos");
        System.out.println("Nombre: " + directorPelicula.getNombre());
        System.out.println("ID: " + directorPelicula.getDirectorId());
    }
    
    // 6. Insertar la película con el director
    inserPeli(session, titulo, fechaEstrenoPelicula, directorPelicula);
    break;

                case 3:
                    System.out.println("Inserte el id de la pelicula a borrar: ");
                    int id = scn.nextInt();

                    borrarPeli(session, id);
                    break;

                case 4:

                    System.out.println("\nIngrese el nombre del director: ");
                    String directorNom = scn.nextLine();

                    peliDirector(session, directorNom);
                    break;

                case 5:
                    System.out.println("Saliendo del programa");
                    break;

                default:
                    System.out.println("Error numero no valido");
                    break;
            }
        }

        session.close();
        System.out.println("Cerrando sesion de Oracle");
    }

    public static void mostrarPeli(Session s){
        try {

            String hql  = "FROM Peliculas";
            List<Peliculas> peliculas = s.createQuery(hql, Peliculas.class).list();
            
            if(!peliculas.isEmpty()){
            for(Peliculas p: peliculas){
                System.out.println(p.getTitulo());}
            }else{System.out.println("No hay peliculas, añadelas primero");}
            
        } catch (Exception e) {
            System.out.println("Error al mostrar las peliculas: " + e.getMessage());
        }
    }

    public static void inserPeli(Session s, String titulo, String fecha_Estreno, Directores d){
        Transaction tx = null;
        try {
            tx = s.beginTransaction();
            Peliculas pelicula = new Peliculas();
            pelicula.setTitulo(titulo);
            pelicula.setFecha_Estreno(fecha_Estreno);
            
            pelicula.setDirector(d);

            s.save(pelicula);
            tx.commit();
            System.out.println("Pelicula añadida");

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            System.out.println("Error al insertar una pelicula: " + e.getMessage());
        }
    }

    public static void borrarPeli(Session s, long id){
        Transaction tx = null;
        try {
            tx = s.beginTransaction();
            String hql = "DELETE FROM Peliculas p WHERE p.peliculaId = :id";
            int filasEliminadas = s.createQuery(hql).setParameter("id", id).executeUpdate();

            tx.commit();

            if (filasEliminadas > 0) {
            System.out.println("Película eliminada correctamente");
            } else {
            System.out.println("No se encontró la película con ID: " + id);
            }
        }catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println("Error al borrar pelicula por id: " + e.getMessage());
        }
    }

    public static void peliDirector(Session s, String dN){
        try {
        String hql = "Select p FROM Peliculas p JOIN p.director d WHERE d.nombre =: nombreDirector";
        List<Peliculas> peliculas = s.createQuery(hql, Peliculas.class)
        .setParameter("nombreDirector", dN)
        .list();

        if (!peliculas.isEmpty()){
        System.out.println(dN + " ha dirigido:\n");
        for(Peliculas p: peliculas){
            System.out.println(p.getTitulo());
        }}else{
            System.out.println(dN + " no esta en la base de datos");
        }
        } catch (Exception e) {
            System.out.println("Error al buscar peli por director " + e.getMessage());
        }
    }

    public static boolean existe(Session s, String d){
        try {

            String hql  = "SELECT COUNT(d) FROM Directores d WHERE d.nombre = :nombre";
            Long count = s.createQuery(hql, Long.class)
            .setParameter("nombre", d)
            .uniqueResult();
            
             return count != null && count > 0;
            
        } catch (Exception e) {
            System.out.println("Error al buscar directores: " + e.getMessage());
        }
        return false;
    }

    public static Directores director(Session s, String d){
        try {

            String hql  = "FROM Directores d WHERE d.nombre = :nombre";
            Directores dir = s.createQuery(hql, Directores.class)
            .setParameter("nombre", d)
            .uniqueResult();

            return dir;
            
        } catch (Exception e) {
            System.out.println("Error al buscar directores: " + e.getMessage());
        }
        return null;
    }

}