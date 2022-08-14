package br.com.guerin.Payload.Cattle;

import br.com.guerin.Entity.Cattle;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class ResultGetSons {
    @Getter
    @Setter
    public Cattle cattle;
    @Getter
    @Setter
    public ArrayList<Cattle> sons;

    public ResultGetSons(Cattle cattle, ArrayList<Cattle> sons) {
        this.cattle = cattle;
        this.sons = sons;
    }
}
