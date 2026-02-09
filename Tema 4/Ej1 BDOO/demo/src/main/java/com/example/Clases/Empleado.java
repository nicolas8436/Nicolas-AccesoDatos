package com.example.Clases;
 
public class Empleado {
    String nombre;
    int antiguedad;
    int edad;
    Hijo inforUnHijo;
    
    public Empleado(String n, int ant, int e, Hijo h) {
        nombre = n;
        antiguedad = ant;
        edad = e;
        inforUnHijo = h;
    }
    
    public void visDatosEmpleados() {
        System.out.println("\n" + nombre + " tiene " + edad + " años, lleva " + antiguedad + " años en la empresa");
        if (inforUnHijo != null) {
            System.out.println("Los datos de su hijo son: ");
            inforUnHijo.visualHijo();
        }
    }
    
    public void cumpleAños() {
        edad++;
    }

    public String getNombre() {
        return nombre;
    }
}