package com.example;

import java.io.File;
import java.util.Scanner;
import com.db4o.*;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.query.Query;
import com.example.Clases.Empleado;
import com.example.Clases.Hijo;

public class App 
{
    public static void main( String[] args )
    {Scanner scanner = new Scanner (System.in);

    EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
        config.file().lockDatabaseFile(false);

        File f = new File("Emple_Hijos.db4o");
                if(f.exists()){
                    f.delete();
                }

        ObjectContainer baseDatos = Db4oEmbedded.openFile(config, "Emple_Hijos.db4o");

        int eleccion;

        do {

        System.out.println("\n=====Menu=====");
        System.out.println("1.Insertar datos de empleado");
        System.out.println("2.Mostrar empleados mayores de una edad");
        System.out.println("3.Incrementar edad de un empleado por nombre");
        System.out.println("4.Borrar empleado con mas antiguedad");
        System.out.println("5.Visualizar empleados y sus hijos");
        System.out.println("0.Salir");
        System.out.println();

       
        eleccion = scanner.nextInt();
        scanner.nextLine();

        switch (eleccion) {
            case 1:
            if (hayEmpleados(baseDatos)) {
                System.out.println("Ya existen los empleados");
            } else {
                baseDatos.store(new Empleado("Angel", 5, 53, new Hijo("Gustavo", 7)));
                baseDatos.store(new Empleado("Nieves", 3, 45, new Hijo("Ivan", 3)));
                baseDatos.store(new Empleado("Jesus", 3, 5, new Hijo("Noelia", 3)));
                baseDatos.store(new Empleado("Dolores", 5, 63, new Hijo("Sergio", 7)));
                baseDatos.store(new Empleado("Vicki", 3, 5, null));
                baseDatos.store(new Empleado("Fatima", 5, 63, new Hijo("Lidia", 27)));
                baseDatos.store(new Empleado("Juan Luis", 3, 5, null));
                baseDatos.store(new Empleado("Elena", 1, 42, new Hijo("David", 19)));
                baseDatos.store(new Empleado("Miguel", 20, 45, new Hijo("Paula", 3)));
                baseDatos.store(new Empleado("Jesus", 19, 44, new Hijo("Ruben", 12)));
                baseDatos.commit();
                System.out.println("Registros añadidos\n");  
            }
                break;

            case 2:
                if (hayEmpleados(baseDatos)) {
                System.out.println("\nIndique la edad: ");
                int minEdad = scanner.nextInt();
                mostrarEdad(minEdad, baseDatos);
                }else{
                    System.out.println("Inserte empleados primero");
                }
                break;

            case 3:
                if (hayEmpleados(baseDatos)) {

                System.out.println("\n Indique el nombre del empleado: ");
                String nomEmple = scanner.nextLine();

                aumentarEdad(baseDatos, nomEmple);

                }else{
                    System.out.println("Inserte empleados primero");
                }
                
                break;

            case 4:
                if (hayEmpleados(baseDatos)) {
                System.out.println("\nBorrando empleado con mas antiguedad...");
                borrarAntiguo(baseDatos);

                }else{
                    System.out.println("Inserte empleados primero");
                }

                break;

            case 5:
                if (hayEmpleados(baseDatos)) {
                System.out.println("\nMostrando empleados y sus hijos");
                mostrarTodo(baseDatos);

                }else{
                    System.out.println("Inserte empleados primero");
                }

                break;

            case 0:
                System.out.println("Saliendo del programa");
                
                break;
        
            default:
                break;
        }
    }while (eleccion != 0);


        scanner.close();
        baseDatos.close();
    }

    public static void mostrarEdad(int edad, ObjectContainer baseDatos){
      Query cons;//consulta
      ObjectSet<Empleado> res;//Respuesta
      Empleado emple; 
      
      System.out.println("===Mostrando empleados===");
      //Consulta
      cons = baseDatos.query();
      cons.constrain(Empleado.class);
      //Condiciones
      cons.descend("edad").constrain(edad).greater();

      res=cons.execute();
      while(res.hasNext()){
        emple=(Empleado)res.next();
        emple.visDatosEmpleados();
      }
    }

    public static void aumentarEdad(ObjectContainer baseDatos, String nombre){
        Query cons;//consulta
      ObjectSet<Empleado> res;//Respuesta
      Empleado emple; 
      
      System.out.println("\nFeliz cumple años " + nombre);
      //Consulta
      cons = baseDatos.query();
      cons.constrain(Empleado.class);
      //Condiciones
      cons.descend("nombre").constrain(nombre).equal();

      res=cons.execute();
        
      res.hasNext();
      emple=(Empleado)res.next();
        
      emple.cumpleAños();
      baseDatos.store(emple);
      baseDatos.commit();
      }

    public static void borrarAntiguo(ObjectContainer baseDatos){
        Query cons;//consulta
      ObjectSet<Empleado> res;//Respuesta
      Empleado emple; 
      
      //Consulta
      cons = baseDatos.query();
      cons.constrain(Empleado.class);
      //Condiciones
      cons.descend("antiguedad").orderDescending();

      res=cons.execute();  
      res.hasNext();
      emple=(Empleado)res.next();
        
      System.out.println("Borrando a " + emple.getNombre());
      baseDatos.delete(emple);
      baseDatos.commit();
    }

    public static void mostrarTodo(ObjectContainer baseDatos){
      Query cons;//consulta
      ObjectSet<Empleado> res;//Respuesta
      Empleado emple; 
      
      System.out.println("===Mostrando empleados e hijos===");
      //Consulta
      cons = baseDatos.query();
      cons.constrain(Empleado.class);
      res=cons.execute();
      while(res.hasNext()){
        emple=(Empleado)res.next();
        emple.visDatosEmpleados();
      }
    }

    public static boolean hayEmpleados(ObjectContainer baseDatos) {
    Query consulta = baseDatos.query();
    consulta.constrain(Empleado.class);  // Buscar empleados
    
    ObjectSet<Empleado> resultados = consulta.execute();
    
    if (resultados.size() > 0) {
        return true;  // Hay empleados
    } else {
        return false; // No hay empleados
    }
}


}
