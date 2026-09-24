package main.java.com.beauty.spa.salon.model;

public class CitaEmpleado {
    private int idCita;
    private int idCliente;
    private int idEmpleado;
    private int idServicio;
    private String fechaHora;
    private String estado;

    public CitaEmpleado() {
    }

    public CitaEmpleado(int idCita, int idCliente, int idEmpleado, int idServicio, String fechaHora, String estado) {
        this.idCita = idCita;
        this.idCliente = idCliente;
        this.idEmpleado = idEmpleado;
        this.idServicio = idServicio;
        this.fechaHora = fechaHora;
        this.estado = estado;
    }

    public CitaEmpleado(int idCliente, int idEmpleado, int idServicio, String fechaHora, String estado) {
        this.idCliente = idCliente;
        this.idEmpleado = idEmpleado;
        this.idServicio = idServicio;
        this.fechaHora = fechaHora;
        this.estado = estado;
    }

    public int getIdCita() { return idCita; }
    public void setIdCita(int idCita) { this.idCita = idCita; }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public int getIdEmpleado() { return idEmpleado; }
    public void setIdEmpleado(int idEmpleado) { this.idEmpleado = idEmpleado; }

    public int getIdServicio() { return idServicio; }
    public void setIdServicio(int idServicio) { this.idServicio = idServicio; }

    public String getFechaHora() { return fechaHora; }
    public void setFechaHora(String fechaHora) { this.fechaHora = fechaHora; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}