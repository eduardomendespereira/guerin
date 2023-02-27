package br.com.guerin.Service;

import br.com.guerin.Entity.Insemination;
import br.com.guerin.Entity.Vaccine;
import br.com.guerin.Repository.Insemination.InseminationRepository;
import br.com.guerin.Service.IService.IInseminationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class InseminationService implements IInseminationService {

    private final InseminationRepository inseminationRepository;


    public Optional<Insemination> findById(Long id){
        return this.inseminationRepository.findById(id);
    }

    public ArrayList<Insemination> findAll()  {
        return (ArrayList<Insemination>) inseminationRepository.findAll();
    }

    @Transactional
    public Insemination saveTransactional(Insemination insemination){
        return this.inseminationRepository.save(insemination);
    }

    public Insemination save(Insemination insemination){
        return saveTransactional(insemination);
    }

    public Insemination update(Insemination insemination){
        return saveTransactional(insemination);
    }


}
