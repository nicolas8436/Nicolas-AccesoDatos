package Clases;

import Interfaces.TemperaturaListener;
import java.util.ArrayList;
import java.util.List;

public class SensorBean {
    private double temperatura;
    private List<TemperaturaListener> listeners = new ArrayList<>();
    
    public void addTemperaturaListener(TemperaturaListener listener) {
        listeners.add(listener);
    }
    
    public double getTemperatura() {
        return temperatura;
    }
    
    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
        
        // si la temperatura supera los 30 grados, se lanza un evento
        if (temperatura > 30) {
            TemperaturaEvent e = new TemperaturaEvent(this, temperatura);
            for (TemperaturaListener l : listeners) {
                l.temperaturaSuperada(e);
            }
        }
    }
}