package br.com.guerin.Entity;

public enum Role {
    admin("admin"),
    user("user");

    public final String value;

    private Role(String value){
        this.value = value;
    }
}
