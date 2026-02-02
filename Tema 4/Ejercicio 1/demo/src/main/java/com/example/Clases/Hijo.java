package com.example.Clases;

public class Hijo {
    String nombre;
    int edad;
    
    public Hijo(String nombre, int ed) {
        this.nombre = nombre;
        edad = ed;
    }
    
    public void visualHijo() {
        System.out.println("\n\tNOMBRE: " + nombre);
        System.out.println("\n\tEDAD: " + edad);
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public int getEdad() {
        return edad;
    }
}