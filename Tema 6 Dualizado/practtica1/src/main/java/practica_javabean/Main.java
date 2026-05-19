package practica_javabean;
import java.util.Vector;

import GestionMatriculas.*;
public class Main {
    public static void main(String[] args) {
        try {
            // 1. Instanciamos el componente JavaBean
            MatriculaBean componente = new MatriculaBean();

            // 2. Registramos el Listener sobrescribiendo el método (Igual al modelo teórico)
            componente.addModoModificadoListener(new MatriculaBean.ModoModificadoListener() {
                @Override
                public void capturarModoModificado(MatriculaBean.ModoModificadoEvent ev) {
                    System.out.println("\n[EVENTO OYENTE] Se ha modificado el modo de visualización.");
                    System.out.println("-> ¿Modo de visualización global (todas)? " + ev.getModo());
                }
            });

            // REQUISITO 1: Listado de todas las matrículas del sistema
            System.out.println("=== REQUISITO 1: MOSTRANDO TODAS LAS MATRÍCULAS ===");
            componente.recargarFilas(); // Esto disparará el evento del Listener (modo = true)
            mostrarMatriculasVector(componente);

            // REQUISITO 2: Listado de un alumno concreto (DNI particular)
            String dniAFiltrar = "12345678A"; 
            System.out.println("\n=== REQUISITO 2: FILTRANDO POR DNI (" + dniAFiltrar + ") ===");
            componente.recargarDNI(dniAFiltrar); // Esto disparará el evento del Listener (modo = false)
            mostrarMatriculasVector(componente);

            // REQUISITO 3: Añadir una nueva matrícula usando los setters y addMatricula()
            System.out.println("\n=== REQUISITO 3: AÑADIENDO NUEVA MATRÍCULA ===");
            componente.setDNI("12345678A");
            componente.setNombreModulo("Desarrollo de Interfaces");
            componente.setCurso("25-26");
            componente.setNota(9.0);
            componente.addMatricula(); // Guarda en BD y actualiza automáticamente el Vector
            System.out.println("¡Matrícula insertada con éxito!");

            // REQUISITO 4: Llamar de nuevo a recargarFilas y volver a mostrar todo
            System.out.println("\n=== REQUISITO 4: VOLVIENDO A CARGAR TODAS TRAS LA INSERCIÓN ===");
            componente.recargarFilas(); // Vuelve a poner el modo a true y notifica al Listener
            mostrarMatriculasVector(componente);

        } catch (ClassNotFoundException e) {
            System.out.println("Error: No se ha podido cargar el driver de la Base de Datos.");
            e.printStackTrace();
        }
    }

    // Método auxiliar estático para recorrer el Vector interno e imprimirlo por pantalla
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