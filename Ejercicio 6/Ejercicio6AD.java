import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileReader;

public class Ejercicio6AD {
    private static final String ARCHIVO_XML = "vehiculo.xml";

    public static void main(String[] args) {
        try {
            // 1. CREAR OBJETO VEHÍCULO COMPLETO
            System.out.println("---CREANDO OBJETO VEHÍCULO---");
            Motor motor = new Motor("Gasolina", 4, 150);
            Ruedas ruedas = new Ruedas(4, "Alloy");
            Vehiculo vehiculo = new Vehiculo("Audi", "A3", motor, ruedas);

            System.out.println("Vehículo creado: " + vehiculo);

            // 2. Serializar a XML
            System.out.println("\n---SERIALIZANDO A XML---");
            JAXBContext context = JAXBContext.newInstance(Vehiculo.class, Motor.class, Ruedas.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

            // Guardar en archivo
            marshaller.marshal(vehiculo, new File(ARCHIVO_XML));
            System.out.println("Archivo XML guardado: " + ARCHIVO_XML);

            // Mostrar por consola
            System.out.println("Contenido del XML:");
            marshaller.marshal(vehiculo, System.out);

            // 3. Leer XML y convertir a objeto
            System.out.println("\n---LEYENDO DESDE XML---");
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Vehiculo vehiculoLeido = (Vehiculo) unmarshaller.unmarshal(new FileReader(ARCHIVO_XML));

            // 4. MOSTRAR DATOS POR CONSOLA
            System.out.println("\n---DATOS LEÍDOS DEL XML---");
            System.out.println("Marca: " + vehiculoLeido.getMarca());
            System.out.println("Modelo: " + vehiculoLeido.getModelo());
            System.out.println("Motor - Combustible: " + vehiculoLeido.getMotor().getCombustible());
            System.out.println("Motor - Cilindros: " + vehiculoLeido.getMotor().getCilindros());
            System.out.println("Motor - Potencia: " + vehiculoLeido.getMotor().getPotencia());
            System.out.println("Ruedas - Número: " + vehiculoLeido.getRuedas().getNumero());
            System.out.println("Ruedas - Tipo: " + vehiculoLeido.getRuedas().getTipo());

            System.out.println("\nObjeto completo reconstruido: " + vehiculoLeido);

        } catch (JAXBException e) {
            System.err.println("Error JAXB: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error general: " + e.getMessage());
            e.printStackTrace();
        }
    }
}