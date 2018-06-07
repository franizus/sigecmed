/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctor;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author frani
 */
public class Especialidad extends RecursiveTreeObject<Especialidad> {
    
    private final SimpleIntegerProperty id_especialidad = new SimpleIntegerProperty(this, "id_especialidad");
    private final SimpleStringProperty nombre = new SimpleStringProperty(this, "nombre");
    
    public Especialidad() {}
    
    public Especialidad(int id_especialidad, String nombre) {
        setId_especialidad(id_especialidad);
        setNombre(nombre);
    }
    
    public IntegerProperty id_especialidadProperty() {
        return id_especialidad;
    }
    
    public final int getId_especialidad() {
        return id_especialidadProperty().get();
    }

    public final void setId_especialidad(int id_especialidad) {
        id_especialidadProperty().set(id_especialidad);
    }

    public StringProperty getNombreProperty() {
        return nombre;
    }
    
    public final String getNombre() {
        return getNombreProperty().get();
    }
    
    public final void setNombre(String nombre) {
        getNombreProperty().set(nombre);
    }
    
}
