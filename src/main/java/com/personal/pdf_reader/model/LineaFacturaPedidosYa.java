package com.personal.pdf_reader.model;

public class LineaFacturaPedidosYa {
    private String producto;
    private String subtotal;
    private String subtotalConIva;

    public LineaFacturaPedidosYa(String producto, String subtotal, String subtotalConIva) {
        this.producto = producto;
        this.subtotal = subtotal;
        this.subtotalConIva = subtotalConIva;
    }

    public String getProducto() {
        return producto;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public String getSubtotalConIva() {
        return subtotalConIva;
    }

    @Override
    public String toString() {
        return "LineaFactura{" +
                "producto='" + producto + '\'' +
                ", subtotal='" + subtotal + '\'' +
                ", subtotalConIva='" + subtotalConIva + '\'' +
                '}';
    }
}
