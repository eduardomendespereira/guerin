package br.com.guerin.entity;

public enum Sex {
    male("Macho"),
    female("Fêmea");

    public final String value;

    private Sex(String value){
        this.value = value;
    }
}
