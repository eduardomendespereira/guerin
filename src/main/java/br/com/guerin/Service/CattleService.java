package br.com.guerin.Service;

import br.com.guerin.Entity.Cattle;
import br.com.guerin.Payload.Cattle.ResultFindParents;
import br.com.guerin.Payload.Cattle.ResultFindChildren;
import br.com.guerin.Repository.Cattle.CattleRepository;
import br.com.guerin.Service.IService.ICattleService;
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
    public Cattle save(Cattle cattle) {  // TODO: ...
        if (cattle.getId() == null & cattle.getEarring() != null && findByEarring(cattle.getEarring()).isPresent())
            return null;
        return this.cattleRepository.save(cattle);
    }

    @Transactional
    public Cattle disable(Long id, Cattle cattle) {
        if (id == cattle.getId()) {
            if (!this.findById(id).get().isInactive()) {
                this.cattleRepository.disable(cattle.getId());
                return this.cattleRepository.findById(id).get();
            }
            else {
                throw new RuntimeException("Gado já está inativo!");
            }
        }
        else {
            throw new RuntimeException("Gado não encontrado");
        }
    }

    public Optional<Cattle> findByEarring(Long earring) {
        return this.cattleRepository.findByEarring(earring);
    }

    public Cattle findByEarringOrNew(Long earring) {
        var cattle = this.cattleRepository.findByEarring(earring);
        if (cattle.isPresent()) {
            return cattle.get();
        }
        return new Cattle(earring);
    }

    public ArrayList<Cattle> findBySpecie(Long specie_id) {
        return this.cattleRepository.findBySpecie(specie_id);
    }

    public ArrayList<Cattle> findByFarm(Long farm_id) {
        return this.cattleRepository.findByFarm(farm_id);
    }

    public ResultFindParents findParents(Long earring) {
        Optional<Cattle> cattle = findByEarring(earring);
        if (cattle.isPresent()) {
            Cattle father = null;
            Cattle mother = null;

            if (cattle.get().getFather() != null) {
                father = findByEarringOrNew(cattle.get().getFather());
            }

            if (cattle.get().getFather() != null) {
                mother = findByEarringOrNew(cattle.get().getMother());
            }

            return new ResultFindParents(cattle.get(), father, mother);
        }
        else {
            throw new RuntimeException("Gado não encontrado");
        }
    }

    public ResultFindChildren findChildren(Long earring) {
        Optional<Cattle> cattle = findByEarring(earring);
        if (cattle.isPresent()) {
            ArrayList<Cattle> children = this.cattleRepository.findChildren(earring);
            return new ResultFindChildren(cattle.get(), children);
        }
        else {
            throw new RuntimeException("Gado não encontrado");
        }
    }
}