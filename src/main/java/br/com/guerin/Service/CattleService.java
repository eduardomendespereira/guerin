package br.com.guerin.Service;

import br.com.guerin.Entity.Cattle;
import br.com.guerin.Entity.Farm;
import br.com.guerin.Entity.Gender;
import br.com.guerin.Entity.Specie;
import br.com.guerin.Payload.Cattle.ResultFindParents;
import br.com.guerin.Payload.Cattle.ResultFindChildren;
import br.com.guerin.Repository.Cattle.CattleRepository;
import br.com.guerin.Service.IService.ICattleService;
import br.com.guerin.Service.IService.IFarmService;
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
public class CattleService implements ICattleService {
    private final CattleRepository cattleRepository;
    private final ISpecieService specieService;
    private final IFarmService farmService;

    public void validateParents(Cattle cattle) {
        this.validateFather(cattle);
        this.validateMother(cattle);
    }

    public void validateFather(Cattle cattle) {
        Long fatherEarring = cattle.getFather();
        if (fatherEarring != null) {
            if (fatherEarring != cattle.getEarring()) {
                Optional<Cattle> cattleFather = this.findByEarring(fatherEarring);
                if (cattleFather.isPresent()) {
                    Gender fatherGender = cattleFather.get().getGender();
                    if (fatherGender != Gender.male) {
                        throw new RuntimeException("Gado informado não pode ser pai");
                    }
                }
                else {
                    throw new RuntimeException("Nao existe no banco");
                }
            }
            else {
                throw new RuntimeException("Gado informado não pode ser pai de si mesmo");
            }
        }
    }

    public void validateMother(Cattle cattle) {
        Long motherEarring = cattle.getMother();
        if (motherEarring != null) {
            if (motherEarring != cattle.getEarring()) {
                Optional<Cattle> cattleMother = this.findByEarring(motherEarring);
                if (cattleMother.isPresent()) {
                    Gender motherGender = cattleMother.get().getGender();
                    if (motherGender != Gender.female) {
                        throw new RuntimeException("Gado informado não pode ser mãe");
                    }
                }
                else {
                    throw new RuntimeException("Nao existe no banco");
                }
            }
            else {
                throw new RuntimeException("Gado informado não pode ser mãe de si mesmo");
            }
        }
    }


    public Optional<Cattle> findById(Long id) {
        Optional<Cattle> cattle = this.cattleRepository.findById(id);
        if (cattle.isPresent()) {
            return cattle;
        } else {
            throw new RuntimeException("O gado informado não foi encontrado!");
        }
    }

    public Page<Cattle> findAll(Pageable pageable) {
        return this.cattleRepository.findAll(pageable);
    }

    @Transactional
    public Cattle update(Long id, Cattle cattle) {
        if (id == cattle.getId()) {
            this.validateParents(cattle);
            return this.cattleRepository.save(cattle);
        }
        else {
            throw new RuntimeException("Gado não encontrado");
        }
    }

    @Transactional
    public Cattle save(Cattle cattle) {
        if (cattle.getId() == null && cattle.getEarring() != null && this.findByEarring(cattle.getEarring()).isPresent()) {
            throw new RuntimeException("Gado já está registrado");
        }
        else {
            this.validateParents(cattle);
            return this.cattleRepository.save(cattle);
        }
    }

    @Transactional
    public Cattle disable(Long id, Cattle cattle) {
        if (id == cattle.getId()) {
            if (!this.findById(id).get().isInactive()) {
                this.cattleRepository.disable(cattle.getId());
                return this.findById(id).get();
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
        if (earring != null) {
            return this.cattleRepository.findByEarring(earring);
        }
        else {
            throw new RuntimeException("Gado não encontrado");
        }
    }

    public Cattle findByEarringOrNew(Long earring) {
        Optional<Cattle> cattle = this.cattleRepository.findByEarring(earring);
        if (cattle.isPresent()) {
            return cattle.get();
        }
        return new Cattle(earring);
    }

    public ArrayList<Cattle> findBySpecie(Long specie_id) {
        if (specie_id != null) {
            Specie specie = this.specieService.findById(specie_id).get();
            return this.cattleRepository.findBySpecie(specie);
        }
        else {
            throw new RuntimeException("Gado não encontrado");
        }
    }

    public ArrayList<Cattle> findByFarm(Long farm_id) {
        if (farm_id != null) {
            Farm farm = this.farmService.findById(farm_id).get();
            return this.cattleRepository.findByFarm(farm);
        }
        else {
            throw new RuntimeException("Gado não encontrado");
        }
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