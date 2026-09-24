package main.java.com.beauty.spa.salon.model;


public class Empleado {

    private int idEmpleado;
    private int idUsuario;
    private String especialidad;
    private String telefono;
    private String horarioTrabajo;

    private String nombre;
    private String apellido;
    private String correo;
    private String nombreUsuario;

    public Empleado() {}

    public Empleado(int idEmpleado, int idUsuario, String especialidad, String telefono, 
                    String horarioTrabajo, String nombre, String apellido, String correo, String nombreUsuario) {
        this.idEmpleado = idEmpleado;
        this.idUsuario = idUsuario;
        this.especialidad = especialidad;
        this.telefono = telefono;
        this.horarioTrabajo = horarioTrabajo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.nombreUsuario = nombreUsuario;
    }

    public Empleado(int idUsuario, String especialidad, String telefono, String horarioTrabajo) {
        this.idUsuario = idUsuario;
        this.especialidad = especialidad;
        this.telefono = telefono;
        this.horarioTrabajo = horarioTrabajo;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getHorarioTrabajo() {
        return horarioTrabajo;
    }

    public void setHorarioTrabajo(String horarioTrabajo) {
        this.horarioTrabajo = horarioTrabajo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getNombreCompleto() {
        return (nombre != null ? nombre : "") + " " + (apellido != null ? apellido : "");
    }
}