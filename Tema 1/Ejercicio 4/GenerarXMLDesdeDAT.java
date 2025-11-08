import java.io.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;

public class GenerarXMLDesdeDAT {

    public static void main(String[] args) {
        // Llamamos al método que convierte DAT a XML
        convertirDatAXml("EMPLEADOS.DAT");
    }

    public static void convertirDatAXml(String nombreArchivoDat) {
        try {
            // Leemos EMPLEADOS.DAT
            FileInputStream archivoEntrada = new FileInputStream(nombreArchivoDat);
            ObjectInputStream ois = new ObjectInputStream(archivoEntrada);

            // Uso de DOM
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();// Fabrica q crea documentos
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();// Maquina de la fabrica que fabrica los
                                                                      // documentos
            Document doc = dBuilder.newDocument();// Documento resultado del montaje de DocumentBuilder

            // Ceamos la raiz y añadimos la raiz
            Element elementoRaiz = doc.createElement("EMPLEADOS");// Creamos la raiz empleados del objeto doc q es el
                                                                  // documento
            doc.appendChild(elementoRaiz);// La creamos como hija del documento

            // Aclaracion Elemento, Esto se usa para crear cada elemento sea raiz o
            // no del documento lo unico q el raiz es hijo del documento y el resto es hijo
            // del q tenga encima

            // Leer archivo y crear XML
            Empleado empleado;// Creamos una variable empleado q apunta a Empleado pero no lo instanciamos
            try {
                // Leer objetos hasta que se acabe el archivo
                while (true) {
                    empleado = (Empleado) ois.readObject();// Con esto leemos el objeto y llenamos la vatiable empleado

                    Element elementoEmpleado = doc.createElement("Empleado");// Creamos el elemento empleado
                    elementoRaiz.appendChild(elementoEmpleado);// Lo hacemos hijo de la raiz
                    // Tu ^ haces hijo al parentesis ^

                    // Crear y añadir elemento <CODIGO>
                    Element codigo = doc.createElement("CODIGO");
                    codigo.appendChild(doc.createTextNode(String.valueOf(empleado.getCodigo())));
                    elementoEmpleado.appendChild(codigo);

                    // Crear y añadir elemento <COMISION>
                    Element comision = doc.createElement("COMISION");
                    comision.appendChild(doc.createTextNode(String.valueOf(empleado.getComision())));// INterior de los
                                                                                                     // parentesis pasa
                                                                                                     // a String el
                                                                                                     // valor de
                                                                                                     // comision y el
                                                                                                     // exterior hace q
                                                                                                     // eso sea hijo de
                                                                                                     // la etiqueta
                                                                                                     // comision
                                                                                                     // String.valueof
                                                                                                     // int ->String
                    elementoEmpleado.appendChild(comision);

                    // Crear y añadir elemento <DIRECCION>
                    Element direccion = doc.createElement("DIRECCION");
                    direccion.appendChild(doc.createTextNode(empleado.getDireccion()));
                    elementoEmpleado.appendChild(direccion);

                    // Crear y añadir elemento <NOMBRE>
                    Element nombre = doc.createElement("NOMBRE");
                    nombre.appendChild(doc.createTextNode(empleado.getNombre()));
                    elementoEmpleado.appendChild(nombre);

                    // Crear y añadir elemento <SALARIO>
                    Element salario = doc.createElement("SALARIO");
                    salario.appendChild(doc.createTextNode(String.valueOf(empleado.getSalario())));
                    elementoEmpleado.appendChild(salario);

                    // Añadir el empleado completo al elemento raíz
                    elementoRaiz.appendChild(elementoEmpleado);
                }
            } catch (EOFException e) {
                // Se lanza cuando se llega al final del archivo - es normal
                System.out.println("Todos los empleados leídos del archivo .DAT");
            }

            // Cerramos el ObjetImputStream
            ois.close();

            // Lo guardamos como XML
            // Crear TransformerFactory para transformar DOM a XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();// Fabrica de transformadores
            Transformer transformer = transformerFactory.newTransformer();// Transformador generado por la fabrica para
                                                                          // transformar archivos

            // Configurar formato del XML
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");// Pone sangrias
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");

            DOMSource source = new DOMSource(doc);// Crea en memoria
            StreamResult result = new StreamResult(new File("EMPLEADOS.XML"));// Crea en memoria donde estara el doc

            transformer.transform(source, result);// Crea el documento con la info en memoria

            System.out.println("Archivo EMPLEADOS.XML creado correctamente!");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}