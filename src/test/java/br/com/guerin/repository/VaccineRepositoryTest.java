package br.com.guerin.repository;

import br.com.guerin.Entity.Vaccine;
import br.com.guerin.Repository.User.VaccineRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class VaccineRepositoryTest {

    @Autowired
    private VaccineRepository vaccineRepository;

    @Test
    @DisplayName("inserindo 1 vaccina e listando")
    public void insertVaccine(){
        Vaccine vaccine = new Vaccine();
        vaccine.setName("vermicida");
        vaccine.setDate(LocalDateTime.now());
        vaccine.setRequired(true);
        vaccineRepository.save(vaccine);
        Integer contVaccine = vaccineRepository.findAll().size();
        assertEquals(1, contVaccine);
    }
}
