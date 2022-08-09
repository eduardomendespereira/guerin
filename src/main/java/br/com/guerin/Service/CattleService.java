package br.com.guerin.Service;

import br.com.guerin.Entity.Cattle;
import br.com.guerin.Repository.User.CattleRepository;
import br.com.guerin.Service.IService.ICattleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class CattleService implements ICattleService {

    @Autowired
    private CattleRepository cattleRepository;

    public Optional<Cattle> findById(Long id) {
        Optional<Cattle> cattle = this.cattleRepository.findById(id);
        if (cattle.isPresent()) {
            return cattle;
        }
        else {
            throw new RuntimeException("O gado informado não foi encontrado!");
        }
    }

    public Page<Cattle> findAll(Pageable pageable) {
        Page<Cattle> cattles = this.cattleRepository.findAll(pageable);
        if (! cattles.isEmpty()) {
            return cattles;
        }
        else {
            throw new RuntimeException("Não há gados registrados!");
        }
    }

    @Transactional
    public void update(Long id, Cattle cattle) {
        if (id == cattle.getId()) {
            this.cattleRepository.save(cattle);
        }
        else {
            throw new RuntimeException("Gado não encontrado");
        }
    }

    @Transactional
    public void insert(Cattle cattle) {
        this.cattleRepository.save(cattle);
    }

    @Transactional
    public void inactivate(Long id, Cattle cattle) {
        if (id == cattle.getId()) {
            if (! this.findById(id).get().isInactive()) {
                this.cattleRepository.inactivate(cattle.getId());
            }
            else {
                throw new RuntimeException("Gado já está inativo!");
            }
        }
        else {
            throw new RuntimeException("Gado não encontrado");
        }
    }
}
