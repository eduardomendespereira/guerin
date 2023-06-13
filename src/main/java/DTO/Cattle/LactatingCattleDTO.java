package DTO.Cattle;

import java.math.BigInteger;
import java.security.Timestamp;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LactatingCattleDTO {
    private BigInteger id;
    private BigInteger earring;
    private BigInteger lactatingChildren;
    private LocalDate lactationEndDate;

    public LactatingCattleDTO(BigInteger id, BigInteger earring, BigInteger lactatingChildren,
            LocalDate lactationEndDate) {
        this.id = id;
        this.earring = earring;
        this.lactatingChildren = lactatingChildren;
        this.lactationEndDate = lactationEndDate;
    }

}