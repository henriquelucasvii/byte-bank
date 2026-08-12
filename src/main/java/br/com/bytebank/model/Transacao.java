package br.com.bytebank.model;

public class Transacao {
    private int id;
    private double valor;
    private String tipo;
    private String data;

    public Transacao(int id, double valor, String tipo, String data) {
        this.id = id;
        this.valor = valor;
        this.tipo = tipo;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}