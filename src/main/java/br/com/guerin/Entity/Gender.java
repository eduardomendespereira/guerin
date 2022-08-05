package br.com.guerin.Entity;

public enum Gender {
    male("Macho"),
    female("Fêmea");

    public final String value;

    private Gender(String value){
        this.value = value;
    }
}
