package br.com.guerin.Service;

import br.com.guerin.Entity.Farm;
import br.com.guerin.Entity.Weighing;
import br.com.guerin.Repository.Farm.FarmRepository;
import br.com.guerin.Service.IService.IFarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class FarmService implements IFarmService {
    @Autowired
    private FarmRepository farmRepository;

    public Optional<Farm> findById(Long id) {
        Optional<Farm> farm = this.farmRepository.findById(id);
        if (farm.isPresent()) {
            return farm;
        }
        else {
            throw new RuntimeException("A Fazenda informada não foi encontrada!");
        }
    }

    public ArrayList<Farm> findAll() {
        return (ArrayList<Farm>) this.farmRepository.findAll();
    }

    @Transactional
    public Farm update(Long id, Farm farm) {
        if (id == farm.getId()) {
            return this.farmRepository.save(farm);
        }
        else {
            throw new RuntimeException("Fazenda não encontrada!");
        }
    }

    @Transactional
    public Farm save(Farm farm) {
        return this.farmRepository.save(farm);
    }

    @Transactional
    public Farm disable(Long id, Farm farm) {
        if (id == farm.getId()) {
            this.farmRepository.disable(id);
            return this.findById(id).get();
        }
        else {
            throw new RuntimeException("Fazenda não encontrada!");
        }
    }

    @Transactional
    public Farm enable(Long id, Farm farm) {
        if (id == farm.getId()) {
            this.farmRepository.enable(id);
            return this.findById(id).get();
        }
        else {
            throw new RuntimeException("Fazenda não encontrada!");
        }
    }

    public Optional<Farm> findByName(String name) {
        return this.farmRepository.findByName(name);
    }
    public Optional<Farm> findByAddress(String address) {
        return this.farmRepository.findByAddress(address);
    }

    public Integer count(){
        Integer count = 0;
        for(Farm farm : farmRepository.findAll()){
            if(!farm.isInactive()){
                count++;
            }
        }
        return count;
    }
}
