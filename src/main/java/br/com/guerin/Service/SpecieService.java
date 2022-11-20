package br.com.guerin.Service;

import br.com.guerin.Entity.Specie;
import br.com.guerin.Repository.Specie.SpecieRepository;
import br.com.guerin.Service.IService.ISpecieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SpecieService implements ISpecieService {
    private final SpecieRepository specieRepository;
    @Transactional
    public Specie save(Specie specie){
         return specieRepository.save(specie);
    }
    public Optional<Specie> findById(Long id){
        return this.specieRepository.findById(id);
    }
    public Optional<Specie> findByName(String name) {
        return this.specieRepository.findByName(name);
    }
    public ArrayList<Specie> listAll(){
        return (ArrayList<Specie>) specieRepository.findAll();
    }
    @Transactional
    public Specie update(Long id, Specie specie){
        if(id == specie.getId()){
            return this.specieRepository.save(specie);
        }else{
            throw  new RuntimeException("Erro : Não foi possivel editar a Especie");
        }
    }
    @Transactional
    public void disable(Long id, Specie specie ){
        if(id == specie.getId()){
            this.specieRepository.desativar(specie.getId());
        }
    }
    public boolean checkAtivo(Specie specie){
        if(specie.isInactive()){
            return false;
        }
        return true;
    }

    public void enable(Long id){
        this.specieRepository.enable(id);
    }

    public Integer count(){
        Integer count = 0;
        for(Specie specie : specieRepository.findAll()){
            if(!specie.isInactive()){
                count++;
            }
        }
        return count;
    }

}
