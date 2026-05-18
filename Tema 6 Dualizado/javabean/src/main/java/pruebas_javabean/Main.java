package pruebas_javabean;

import Clases.TemperaturaEvent;
import Interfaces.TemperaturaListener;
import Clases.SensorBean;

public class Main {
    public static void main(String[] args) {
        SensorBean sensor = new SensorBean();
        
        // añadimos un listener al sensor
        // sobreescribiendo el método temperaturaSuperada para mostrar un mensaje
        // cuando se supere la temperatura
        sensor.addTemperaturaListener(new TemperaturaListener() {
            @Override
            public void temperaturaSuperada(TemperaturaEvent e) {
                System.out.println("¡Temperatura superada! Temperatura actual: " + e.getTemperatura());
            }
        });
        
        // simulamos la lectura de la temperatura
        sensor.setTemperatura(25); // no se lanza el evento
        sensor.setTemperatura(28); // no se lanza el evento
        sensor.setTemperatura(31); // se lanza el evento y se muestra el mensaje
        sensor.setTemperatura(33);
    }
}