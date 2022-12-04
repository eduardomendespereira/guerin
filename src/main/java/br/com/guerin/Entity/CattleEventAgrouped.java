package br.com.guerin.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@AllArgsConstructor
public class CattleEventAgrouped{
    @Getter @Setter
    private String mode;
    @Getter @Setter
    private String label;
    @Getter @Setter
    private Boolean html;
    @Getter @Setter
    private Long count;
    @Getter @Setter
    private ArrayList<CattleEvent> children;
}
