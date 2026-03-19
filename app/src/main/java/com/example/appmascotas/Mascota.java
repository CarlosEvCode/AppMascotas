package com.example.appmascotas;

public class Mascota {
    private int id;
    private String tipo;
    private String nombre;
    private String color;
    private String pesokg;

    public Mascota(int id, String tipo, String nombre, String color, String pesokg) {
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

    public String getPesokg() {
        return pesokg;
    }
}
