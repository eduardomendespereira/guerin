package br.com.guerin.entity;

public enum Sex {
    macho("Macho"),
    femea("Fêmea");

    public final String valor;

    private Sex(String valor){
        this.valor = valor;
    }
}
