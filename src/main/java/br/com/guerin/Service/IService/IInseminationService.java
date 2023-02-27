package br.com.guerin.Service.IService;

import br.com.guerin.Entity.Insemination;
import br.com.guerin.Entity.Vaccine;

import java.util.ArrayList;
import java.util.Optional;

public interface IInseminationService {
    void disable(Long id);
    void enable(Long id);
    Optional<Insemination> findById(Long id);
    ArrayList<Insemination> findAll();
    Insemination update(Insemination insemination);
    Insemination saveTransactional(Insemination insemination);
    Insemination save(Insemination insemination);
    public Integer count();
}
