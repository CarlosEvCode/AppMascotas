package com.example.appmascotas;

public class Mascota {
    private int id;
    private String tipo;
    private String nombre;
    private String color;
    private Double pesokg;

    public Mascota(int id, String tipo, String nombre, String color, Double pesokg) {
        this.id = id;
        this.tipo = tipo;
        this.nombre = nombre;
        this.color = color;
        this.pesokg = pesokg;
    }

    public Mascota() {
    }

    public int getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getColor() {
        return color;
    }

    public Double getPesokg() {
        return pesokg;
    }
}
