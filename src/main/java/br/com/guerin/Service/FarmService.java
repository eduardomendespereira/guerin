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
    public void disable(Long id) {
        this.farmRepository.disable(id);
    }

    public Optional<Farm> findByName(String name) {
        return this.farmRepository.findByName(name);
    }
    public Optional<Farm> findByAddress(String address) {
        return this.farmRepository.findByAddress(address);
    }

}
