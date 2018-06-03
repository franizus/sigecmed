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
import secretaria.Secretaria;

/**
 *
 * @author frani
 */
public class Doctor extends RecursiveTreeObject<Doctor> {
    
    private final SimpleIntegerProperty id_doctor = new SimpleIntegerProperty(this, "id_doctor");
    private final SimpleIntegerProperty id_especialidad = new SimpleIntegerProperty(this, "id_especialidad");
    private final SimpleStringProperty nombre = new SimpleStringProperty(this, "nombre");
    private final SimpleStringProperty cedula = new SimpleStringProperty(this, "cedula");
    private final SimpleStringProperty direccion = new SimpleStringProperty(this, "direccion");
    private final SimpleStringProperty telefono = new SimpleStringProperty(this, "telefono");
    private final SimpleStringProperty correo = new SimpleStringProperty(this, "correo");
    
    public Doctor() {}
    
    public Doctor(int id_doctor, int id_especialidad, String nombre, String cedula, String direccion, String telefono, String correo) {
        setId_doctor(id_doctor);
        setId_especialidad(id_especialidad);
        setNombre(nombre);
        setCedula(cedula);
        setDireccion(direccion);
        setTelefono(telefono);
        setCorreo(correo);
    }
    
    public IntegerProperty id_doctorProperty() {
        return id_doctor;
    }
    
    public final int getId_doctor() {
        return id_doctorProperty().get();
    }

    public final void setId_doctor(int id_doctor) {
        id_doctorProperty().set(id_doctor);
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

    public StringProperty getCedulaProperty() {
        return cedula;
    }
    
    public final String getCedula() {
        return getCedulaProperty().get();
    }
    
    public final void setCedula(String cedula) {
        getCedulaProperty().set(cedula);
    }

    public StringProperty getDireccionProperty() {
        return direccion;
    }
    
    public final String getDireccion() {
        return getDireccionProperty().get();
    }
    
    public final void setDireccion(String direccion) {
        getDireccionProperty().set(direccion);
    }

    public StringProperty getTelefonoProperty() {
        return telefono;
    }
    
    public final String getTelefono() {
        return getTelefonoProperty().get();
    }
    
    public final void setTelefono(String telefono) {
        getTelefonoProperty().set(telefono);
    }

    public StringProperty getCorreoProperty() {
        return correo;
    }
    
    public final String getCorreo() {
        return getCorreoProperty().get();
    }
    
    public final void setCorreo(String correo) {
        getCorreoProperty().set(correo);
    }
    
}
