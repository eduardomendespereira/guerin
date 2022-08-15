package br.com.guerin.Payload.Cattle;

import br.com.guerin.Entity.Cattle;
import lombok.Getter;
import lombok.Setter;

public class ResultGetFathers {
    @Getter
    @Setter
    public Cattle son;

    @Getter
    @Setter
    public Cattle father;

    @Getter
    @Setter
    public Cattle mother;

    public ResultGetFathers(Cattle son, Cattle father, Cattle mother) {
        this.son = son;
        this.father = father;
        this.mother = mother;
    }
}
