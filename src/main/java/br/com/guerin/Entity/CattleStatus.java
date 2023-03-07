package br.com.guerin.Entity;

public enum CattleStatus {
    cria("Cria"),
    recria("Recria"),
    engorda("Engorda");

    public final String value;

    private CattleStatus(String value) {this.value = value;}
}
