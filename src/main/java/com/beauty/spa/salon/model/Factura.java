package main.java.com.beauty.spa.salon.model;

public class Factura {
    private int idFactura;
    private int idCliente;
    private String fechaFactura;
    private double total;

    public Factura() {}

    public Factura(int idFactura, int idCliente, String fechaFactura, double total) {
        this.idFactura = idFactura;
        this.idCliente = idCliente;
        this.fechaFactura = fechaFactura;
        this.total = total;
    }

    public Factura(int idCliente, String fechaFactura, double total) {
        this.idCliente = idCliente;
        this.fechaFactura = fechaFactura;
        this.total = total;
    }

    public int getIdFactura() { return idFactura; }
    public void setIdFactura(int idFactura) { this.idFactura = idFactura; }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public String getFechaFactura() { return fechaFactura; }
    public void setFechaFactura(String fechaFactura) { this.fechaFactura = fechaFactura; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
}