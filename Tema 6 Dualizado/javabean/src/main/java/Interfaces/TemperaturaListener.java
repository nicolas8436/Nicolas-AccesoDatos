package Interfaces;

import Clases.TemperaturaEvent;
import java.util.EventListener;

public interface TemperaturaListener extends EventListener {
    void temperaturaSuperada(TemperaturaEvent e);
}
