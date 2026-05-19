package practica_javabean;
import java.util.Vector;

import GestionMatriculas.*;
public class Main {
    public static void main(String[] args) {
        try {
            MatriculaBean componente = new MatriculaBean();

            componente.addModoModificadoListener(new MatriculaBean.ModoModificadoListener() {
                @Override
                public void capturarModoModificado(MatriculaBean.ModoModificadoEvent ev) {
                    System.out.println("\n[EVENTO OYENTE] Se ha modificado el modo de visualización.");
                }
            });

            System.out.println("=== MOSTRANDO TODAS LAS MATRÍCULAS ===");
            componente.recargarFilas(); 
            mostrarMatriculasVector(componente);

            String dniAFiltrar = "12345678A"; 
            System.out.println("\n=== FILTRANDO POR DNI (" + dniAFiltrar + ") ===");
            componente.recargarDNI(dniAFiltrar); 
            mostrarMatriculasVector(componente);

            System.out.println("\n=== AÑADIENDO NUEVA MATRÍCULA ===");
            componente.setDNI("12345678A");
            componente.setNombreModulo("Desarrollo de Interfaces");
            componente.setCurso("25-26");
            componente.setNota(9.0);
            componente.addMatricula(); 
            System.out.println("¡Matrícula insertada con éxito!");

            System.out.println("\n=== VOLVIENDO A CARGAR TODAS TRAS LA INSERCIÓN ===");
            componente.recargarFilas(); 
            mostrarMatriculasVector(componente);

        } catch (ClassNotFoundException e) {
            System.out.println("Error: No se ha podido cargar el driver de la Base de Datos.");
            e.printStackTrace();
        }
    }

    private static void mostrarMatriculasVector(MatriculaBean bean) {
        Vector<MatriculaBean.Matricula> lista = bean.getMatriculas();
        if (lista.isEmpty()) {
            System.out.println("No hay registros que mostrar.");
        } else {
            for (MatriculaBean.Matricula m : lista) {
                System.out.println("- Alumno: " + m.dni + " | Módulo: " + m.nombreModulo + " | Curso: " + m.curso + " | Nota: " + m.nota);
            }
        }
    }
}