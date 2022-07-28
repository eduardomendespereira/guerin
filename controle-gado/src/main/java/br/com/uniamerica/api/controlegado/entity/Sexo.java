package br.com.uniamerica.api.controlegado.entity;

public enum Sexo {
    macho("Macho"),
        femea("Fêmea");

    public final String valor;

    private Sexo(String valor){
        this.valor = valor;
    }
}
