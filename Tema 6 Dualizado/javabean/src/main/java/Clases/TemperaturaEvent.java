package Clases;

import java.util.EventObject;

public class TemperaturaEvent extends EventObject {
    private double temperatura;
    
    public TemperaturaEvent(Object source, double temperatura) {
        super(source);
        this.temperatura = temperatura;
    }
    
    public double getTemperatura() {
        return temperatura;
    }
}