package br.com.guerin.Payload.Cattle;

import br.com.guerin.Entity.Cattle;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class ResultFindChildren {
    @Getter
    @Setter
    public Cattle cattle;
    @Getter
    @Setter
    public ArrayList<Cattle> children;

    public ResultFindChildren(Cattle cattle, ArrayList<Cattle> children) {
        this.cattle = cattle;
        this.children = children;
    }
}
