package br.com.uniamerica.api.controlegado.entity;

public enum TipoVacina {
    raiva("Raiva"),
        carbunculo("Carbunculo"),
            vitaminica("Vitaminica"),
                vermifica("Vermifica"),
                    outras("Outras");

    public final String valor;

    private TipoVacina(String valor){
    this.valor = valor;
    }
}
