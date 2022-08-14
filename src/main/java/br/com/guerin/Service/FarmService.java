package br.com.guerin.Service;

import br.com.guerin.Entity.Farm;
import br.com.guerin.Repository.Farm.FarmRepository;
import br.com.guerin.Service.IService.IFarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    public Page<Farm> findAll(Pageable pageable) {
        Page<Farm> farms = this.farmRepository.findAll(pageable);
        if (! farms.isEmpty()) {
            return farms;
        }
        else {
            throw new RuntimeException("Não há Fazendas registradas!");
        }
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
    public void inactivate(Long id, Farm farm) {
        if (id == farm.getId()) {
            if (! this.findById(id).get().isInactive()) {
                this.farmRepository.inactivate(farm.getId());
            }
            else {
                throw new RuntimeException("Fazenda já está inativa!");
            }
        }
        else {
            throw new RuntimeException("Fazenda não encontrada!");
        }
    }

    public Farm findByName(String name) {
        Farm farm = this.farmRepository.findByName(name);
        if (farm != null && ! farm.isInactive()) {
            return farm;
        }
        else {
            throw new RuntimeException("Fazenda não encontrada!");
        }
    }
}
