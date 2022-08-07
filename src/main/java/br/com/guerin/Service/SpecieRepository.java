package br.com.guerin.Service;

import br.com.guerin.Entity.Specie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpecieRepository {
    @Autowired
    private br.com.guerin.Repository.User.SpecieRepository specieRepository;

    private Specie save(Specie specie){
        return specieRepository.save(specie);
    }
}
