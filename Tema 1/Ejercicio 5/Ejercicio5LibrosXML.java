import java.io.File;
import java.io.FileReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Ejercicio5LibrosXML {

    public static void main(String[] args) {
        String archivoXML = "libros.xml";

        System.out.println("------------- COMIENZO LECTURA DOM -------------");
        leerConDOM(archivoXML);

        System.out.println("\n------------- COMIENZO LECTURA STAX -------------");
        leerConSTAX(archivoXML);
    }

    // Método para leer usando DOM
    public static void leerConDOM(String archivo) {
        try {
            File archivoXML = new File(archivo);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(archivoXML);
            doc.getDocumentElement().normalize();

            NodeList listaNodosLibro = doc.getElementsByTagName("libro");

            for (int i = 0; i < listaNodosLibro.getLength(); i++) {
                Node nodoLibro = listaNodosLibro.item(i);

                if (nodoLibro.getNodeType() == Node.ELEMENT_NODE) {
                    Element elementoLibro = (Element) nodoLibro;

                    System.out.println("Elemento: libro");
                    System.out.println("Año: " + elementoLibro.getAttribute("año"));

                    // Procesar autores
                    NodeList listaAutores = elementoLibro.getElementsByTagName("autor");
                    StringBuilder autores = new StringBuilder();

                    for (int j = 0; j < listaAutores.getLength(); j++) {
                        Element elementoAutor = (Element) listaAutores.item(j);
                        String apellido = elementoAutor.getElementsByTagName("apellido").item(0).getTextContent();
                        String nombre = elementoAutor.getElementsByTagName("nombre").item(0).getTextContent();

                        if (j > 0) {
                            autores.append("; ");
                        }
                        autores.append(apellido).append(", ").append(nombre);
                    }

                    System.out.println("Autor: " + autores.toString());

                    // Obtener título
                    String titulo = elementoLibro.getElementsByTagName("titulo").item(0).getTextContent();
                    System.out.println("Titulo: " + titulo);

                    // Obtener editorial
                    String editorial = elementoLibro.getElementsByTagName("editorial").item(0).getTextContent();
                    System.out.println("Editorial: " + editorial);

                    // Obtener precio
                    String precio = elementoLibro.getElementsByTagName("precio").item(0).getTextContent().trim();
                    System.out.println("Precio: " + precio);
                    System.out.println();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para leer usando STAX
    public static void leerConSTAX(String archivo) {
        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLStreamReader reader = factory.createXMLStreamReader(new FileReader(archivo));

            String año = "";
            StringBuilder autores = new StringBuilder();
            String titulo = "";
            String editorial = "";
            String precio = "";
            boolean enLibro = false;
            boolean enAutor = false;
            String apellido = "";
            String nombre = "";
            String elementoActual = "";

            while (reader.hasNext()) {
                int eventType = reader.next();

                switch (eventType) {
                    case XMLStreamConstants.START_ELEMENT:
                        String elemento = reader.getLocalName();
                        elementoActual = elemento;

                        if ("libro".equals(elemento)) {
                            enLibro = true;
                            año = reader.getAttributeValue(null, "año");
                            autores.setLength(0);
                            System.out.println("Elemento: libro");
                            System.out.println("Año: " + año);
                        } else if ("autor".equals(elemento)) {
                            enAutor = true;
                            apellido = "";
                            nombre = "";
                        }
                        break;

                    case XMLStreamConstants.CHARACTERS:
                        String texto = reader.getText().trim();
                        if (!texto.isEmpty() && enLibro) {
                            switch (elementoActual) {
                                case "apellido":
                                    apellido = texto;
                                    break;
                                case "nombre":
                                    nombre = texto;
                                    break;
                                case "titulo":
                                    titulo = texto;
                                    break;
                                case "editorial":
                                    editorial = texto;
                                    break;
                                case "precio":
                                    precio = texto;
                                    break;
                            }
                        }
                        break;

                    case XMLStreamConstants.END_ELEMENT:
                        String elementoFin = reader.getLocalName();

                        if ("autor".equals(elementoFin) && enAutor) {
                            if (autores.length() > 0) {
                                autores.append("; ");
                            }
                            autores.append(apellido).append(", ").append(nombre);
                            enAutor = false;
                        } else if ("libro".equals(elementoFin) && enLibro) {
                            System.out.println("Autor: " + autores.toString());
                            System.out.println("Titulo: " + titulo);
                            System.out.println("Editorial: " + editorial);
                            System.out.println("Precio: " + precio);
                            System.out.println();
                            enLibro = false;
                        }
                        break;
                }
            }

            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}