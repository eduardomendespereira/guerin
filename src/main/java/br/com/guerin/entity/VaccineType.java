package br.com.guerin.entity;

public enum VaccineType {
    rage("Raiva"),
        carbuncle("Carbúnculo"),
            vitaminic("Vitaminica"),
                worm("Vermifica"),
                    outher("Outra");

    public final String value;

    private VaccineType(String value){
        this.value = value;
    }
}
