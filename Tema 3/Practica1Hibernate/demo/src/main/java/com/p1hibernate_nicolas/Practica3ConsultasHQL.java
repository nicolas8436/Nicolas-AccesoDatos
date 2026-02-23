package com.p1hibernate_nicolas;

import config.HibernateUtil;
import entities.Alumno;
import entities.Centro;
import entities.Modulo;
import org.hibernate.Session;
import org.hibernate.query.Query;
import java.util.List;

public class Practica3ConsultasHQL {
    
    public static void main(String[] args) {
        
        Session session = HibernateUtil.get().openSession();
        
        consulta1(session);
        consulta2(session);
        consulta3(session);
        consulta4(session);
        consulta5(session);
        consulta6(session);
        
        
        session.close();
        HibernateUtil.shutdown();
    }
    
    
    public static void consulta1(Session session){
        //1.HQL consulta
        String hql1 = "Select a From Alumno a " + //Seleccionamos alumnos 
        " where a.centro.nombre = CIFP CUENCA" +  //que su centro sea el CIFP
        "AND SIZE(a.modulos) > 2";                //Y que Tengan mas de dos modulos

        //2.Crear la query Lo q devuelve
        Query<Alumno> query = session.createQuery(hql1, Alumno.class);
    
        //3.Ejecucion y obtencion de resultados
        List<Alumno> alumnos = query.getResultList();

        //4.Mostrar resultados
        for (Alumno alumno : alumnos) {
            System.out.println(alumno.getNombre() + "(Modulos: " + alumno.getModulos().size() + " )");
        }
    }

    public static void consulta2 (Session session){
        String hql2 = "SELECT a.centro.nombre, Count(a) FROM Alumno a "//Seleccionamos el nombre del centro y conatamos cuantas veces esta
                        +"GROUP BY a.centro.nombre ";//agrupacion de centros

        Query<Object[]> query = session.createQuery(hql2, Object[].class);

        List<Object[]> centros = query.getResultList();

        for (Object[] centro : centros){
            System.out.println(centro[0] + " numero de alumnos: " + centro[1]);
        }
    }
    
    public static void consulta3 (Session session){//Centro con mas de dos alumnos
        String hql3 = "Select a FROM Alumno a " + 
                      "where a.centro IN ("+
                      "Select c From centro c  " +
                      "join c.alumnos a" +
                      "Group By c " + 
                      "Having count(a) > 2)";

        Query<Alumno> query = session.createQuery(hql3, Alumno.class);

        List<Alumno> alumnos = query.getResultList();

        for (Alumno a : alumnos){
            System.out.println(a.getNombre() + " Esta en el centro: " + a.getCentro().getNombre());
        }
    }

    public static void consulta4 (Session session){//Nombres por encima de la media
        String hql4 = "Select a.nombre From Alumno a"+
                      "where a.edad > (" +
                      "Select avg(a2.edad) from Alumno a2" +
                      "where a.centro = a2.centro)";

        Query<String> query = session.createQuery(hql4, String.class);

        List<String> nombres = query.getResultList();

        for(String s : nombres){
            System.out.println(s);
        }
    }

    public static void consulta5 (Session session){
        String hql5 = "Select a From Alumno a " +
                      "Where a.edad in (" +
                      "Select Max(a2.edad) From Alumno a2 )";

        Query<Alumno> query = session.createQuery(hql5, Alumno.class);
        
        List<Alumno> alumnos = query.getResultList();
        
        for(Alumno a : alumnos){
            System.out.println( a.getNombre() + " Modulos:\n");
            for (Modulo m : a.getModulos()){
                System.out.println(m.getNombre());
            }
        }
    }

    public static void consulta6 (Session session){
        String hql6 = "Select m From modulo m join m.alumnos a" +
                      "where a.edad >= 18 and m.nombre like 'P%'";

        Query<Modulo> query = session.createQuery(hql6, Modulo.class);

        List<Modulo> modulos = query.getResultList();

        for(Modulo m : modulos){
            System.out.println(m.getNombre());

            for(Alumno a : m.getAlumnos()){
                if (a.getEdad() > 18=){
                    System.out.println(a.getNombre());
                }
            }
        }
    }
}
