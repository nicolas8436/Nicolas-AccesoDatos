package com.p1hibernate_nicolas;

import config.HibernateUtil;
import entities.Alumno;
import entities.Centro;
import entities.Modulo;
import repositories.AlumnoRepository;
//import repositories.CentroRepository;
import java.util.ArrayList;
import org.hibernate.Session;

public class Main {
    public static void main(String[] args) {
        System.out.println("Iniciando sesion a MariaDB");
        Session session = HibernateUtil.get().openSession();
        // AQUI la logica del programa
        Centro centro = new Centro("CIFP Nsi");
        ArrayList<Modulo> modulos = new ArrayList<>();
        Modulo prog = new Modulo("Programacion", "PR");
        Modulo datos = new Modulo("Acceso a datos", "AD");
        Modulo movil = new Modulo("Prog. dispositivos móviles", "PMD");
        modulos.add(prog);
        modulos.add(datos);
        modulos.add(movil);
        Alumno alumno = new Alumno(1, "Francisco", "222222F", centro, modulos, 20);
        AlumnoRepository alumnoRepository = new AlumnoRepository(session);
        alumnoRepository.save(alumno);
        session.close();
        System.out.println("Cerrando sesion a MariaDB");
    }
}