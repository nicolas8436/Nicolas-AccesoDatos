package com.repaso1;

import Config.HibernateUtil;
import Entities.Equipos;
import Entities.Poderes;
import Entities.Superheroes;
import org.hibernate.Session;
import org.hibernate.query.Query;
import java.util.List;

public class ConsultasHQL {
    
    public static void main(String[] args) {
        
        Session session = HibernateUtil.get().openSession();
        String equipo = ""; int numero = 0;

        Consulta1(session, equipo);
        Consulta2(session, numero);
        Consulta3(session);
         
        session.close();
        HibernateUtil.shutdown();
    }


    public static void Consulta1(Session session, String equipo){
        String hql1 = "From Equipo e where e.nombre LIKE '%" + equipo + "%'";

        Query<Equipos> query = session.createQuery(hql1, Equipos.class);

        List<Equipos> equipos = query.getResultList();

        for (Equipos e : equipos){
            System.out.println("Nombre equipo: " + e.getNombre() + " Base Secreta: " + e.getBase_secreta());
            
            for(Superheroes s : e.getSuperheroe()){
                System.out.println("Nombre superheroe: " + s.getNombre_heroe());
            }
        }
    }

    public static void Consulta2(Session session, int poderes){
        String hql2 = "Select s.nombre From Superheroes s Where size(s.poder) = " + poderes;

        Query<Object> query = session.createQuery(hql2, Object.class);

        List<Object> supers = query.getResultList();
        
        if (supers.size() == 0){
            System.out.println("No hay heroes con " + poderes + " poderes");
        }else{
            System.out.println("Supers con " + poderes +" poderes:");
            for(Object s : supers){
                System.out.println(s);
            }
        }
    }

    public static void Consulta3(Session session){
        String hql3 = "Select e.nombre, e.base_secreta From Equipo e where size(e.superheroe) > 1 ";

        Query<Object[]> query = session.createQuery(hql3, Object[].class);

        List<Object[]> equipo = query.getResultList();

        for(Object[] e : equipo){
            System.out.println("Nombre equipo: " + e[0] + " Base secreta: " + e[1]);
        }
    }

    public static void Consulta4(Session session, int num){
        String hql4 = "Select e.nombre From Equipos e Where size(e.superheroe) = " + num;

        Query<Object> query = session.createQuery(hql4, Object.class);

        List<Object> equipo = query.getResultList();
        
        if (equipo.size() == 0){
            System.out.println("No hay equipos con " + num + " supers");
        }else{
            System.out.println("Equipos con " + num +" supers:");
            for(Object e : equipo){
                System.out.println(e);
            }
        }
    }

    public static void Consulta5(Session session){
        String hql5 = "Select s.nombre, s.nivel, s.edad, s.equipo.nombre From Superheroes s where size(s.poderes) > 1 ";

        Query<Object[]> query = session.createQuery(hql5, Object[].class);

        List<Object[]> supers = query.getResultList();

        for(Object[] s : supers){
            System.out.println("Nombre heroe: " + s[0] + " Nivel: " + s[1] + " Edad: " + s[2] + " Nombre equipo: " + s[3]);
        }
    }


}
