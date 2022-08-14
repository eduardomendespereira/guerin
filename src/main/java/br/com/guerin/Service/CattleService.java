package br.com.guerin.Service;

import br.com.guerin.Entity.Cattle;
import br.com.guerin.Payload.Cattle.ResultGetFathers;
import br.com.guerin.Payload.Cattle.ResultGetSons;
import br.com.guerin.Repository.Cattle.CattleRepository;
import br.com.guerin.Service.IService.ICattleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CattleService implements ICattleService {
    private final CattleRepository cattleRepository;

    public Optional<Cattle> findById(Long id) {
        Optional<Cattle> cattle = this.cattleRepository.findById(id);
        if (cattle.isPresent()) {
            return cattle;
        } else {
            throw new RuntimeException("O gado informado não foi encontrado!");
        }
    }

    public Page<Cattle> findAll(Pageable pageable) {
        Page<Cattle> cattles = this.cattleRepository.findAll(pageable);
        if (!cattles.isEmpty()) {
            return cattles;
        } else {
            throw new RuntimeException("Não há gados registrados!");
        }
    }

    @Transactional
    public Cattle update(Long id, Cattle cattle) {
        if (id == cattle.getId()) {
            return this.cattleRepository.save(cattle);
        } else {
            throw new RuntimeException("Gado não encontrado");
        }
    }

    @Transactional
    public Cattle save(Cattle cattle) {
        if (cattle.getEarring() != null && findByEarring(cattle.getEarring()) != null)
            return null;
        return this.cattleRepository.save(cattle);
    }

    @Transactional
    public void inactivate(Long id, Cattle cattle) {
        if (id == cattle.getId()) {
            if (!this.findById(id).get().isInactive()) {
                this.cattleRepository.inactivate(cattle.getId());
            } else {
                throw new RuntimeException("Gado já está inativo!");
            }
        } else {
            throw new RuntimeException("Gado não encontrado");
        }
    }

    @Transactional
    public Cattle findByEarring(Long earring) {
        return this.cattleRepository.findByEarring(earring);
    }

    @Transactional
    public Cattle findByEarringOrNew(Long earring) {
        var cattle = this.cattleRepository.findByEarring(earring);
        if (cattle != null)
            return cattle;
        return new Cattle(earring);
    }

    @Transactional
    public ArrayList<Cattle> findBySpecie(Long specie_id) {
        return this.cattleRepository.findBySpecie(specie_id);
    }

    @Transactional
    public ArrayList<Cattle> findByFarm(Long farm_id) {
        return this.cattleRepository.findByFarm(farm_id);
    }

    @Transactional
    public ResultGetFathers getFathers(Long earring) {
        var cattle = findByEarring(earring);
        if (cattle != null) {
            Cattle father = null;
            Cattle mother = null;
            if (cattle.getFather() != null)
                father = findByEarringOrNew(cattle.getFather());
            if (cattle.getMother() != null)
                mother = findByEarringOrNew(cattle.getMother());
            return new ResultGetFathers(cattle, father, mother);
        } else {
            throw new RuntimeException("Not Found");
        }
    }

    @Transactional
    public ResultGetSons getSons(Long earring) {
        var cattle = findByEarring(earring);
        if (cattle != null) {
            var sons = this.cattleRepository.getSons(earring);
            return new ResultGetSons(cattle, sons);
        } else {
            throw new RuntimeException("Not Found");
        }
    }
}