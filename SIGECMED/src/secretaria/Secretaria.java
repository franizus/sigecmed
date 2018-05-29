/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package secretaria;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author frani
 */
public class Secretaria extends RecursiveTreeObject<Secretaria> {

    private final SimpleIntegerProperty id_secretaria = new SimpleIntegerProperty(this, "id_secretaria");
    private final SimpleStringProperty nombre = new SimpleStringProperty(this, "nombre");
    private final SimpleStringProperty cedula = new SimpleStringProperty(this, "cedula");
    private final SimpleStringProperty direccion = new SimpleStringProperty(this, "direccion");
    private final SimpleStringProperty telefono = new SimpleStringProperty(this, "telefono");
    private final SimpleStringProperty correo = new SimpleStringProperty(this, "correo");

    public Secretaria() {}
    
    public Secretaria(int id_secretaria, String nombre, String cedula, String direccion, String telefono, String correo) {
        setId_secretaria(id_secretaria);
        setNombre(nombre);
        setCedula(cedula);
        setDireccion(direccion);
        setTelefono(telefono);
        setCorreo(correo);
    }

    public IntegerProperty id_secretariaProperty() {
        return id_secretaria;
    }
    
    public final int getId_secretaria() {
        return id_secretariaProperty().get();
    }

    public final void setId_secretaria(int id_secretaria) {
        id_secretariaProperty().set(id_secretaria);
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