package br.com.guerin.Service;

import br.com.guerin.Entity.*;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.time.temporal.ChronoUnit;

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
//                else {
//                    throw new RuntimeException("Nao existe no banco");
//                }
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
//                else {
//                    throw new RuntimeException("Nao existe no banco");
//                }
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

    public ArrayList<Cattle> findAll() {
        return (ArrayList<Cattle>) this.cattleRepository.findAll();
    }

    @Transactional
    public Cattle update(Long earring, Cattle cattle) {
        if (Objects.equals(earring, cattle.getEarring())) {
            this.validateParents(cattle);
            this.validateBreed(cattle);
            cattle = this.validateBreastFeeding(cattle);
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
            this.validateBreed(cattle);
            cattle = this.validateBreastFeeding(cattle);
            return this.cattleRepository.save(cattle);
        }
    }

    @Transactional
    public Cattle disable(Long earring, Cattle cattle) {
        if (Objects.equals(earring, cattle.getEarring())) {
            if (!this.findByEarring(earring).get().isInactive()) {
                this.cattleRepository.disable(cattle.getEarring());
                return this.findByEarring(earring).get();
            }
            else {
                throw new RuntimeException("Gado já está inativo!");
            }
        }
        else {
            throw new RuntimeException("Gado não encontrado");
        }
    }

    @Transactional
    public Cattle enable(Long earring, Cattle cattle) {
        if (Objects.equals(earring, cattle.getEarring())) {
            if (this.findByEarring(earring).get().isInactive()) {
                this.cattleRepository.enable(cattle.getEarring());
                return this.findByEarring(earring).get();
            }
            else {
                throw new RuntimeException("Gado já está ativo!");
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

    public Integer count(){
        Integer count = 0;
        for(Cattle cattle : cattleRepository.findAll()){
            if(!cattle.isInactive()){
                count++;
            }
        }
        return count;
    }

    public Integer countMale(){
        Integer countMale = 0;
        for(Cattle cattle : cattleRepository.findAll()){
            if(cattle.getGender() == Gender.male){
                countMale++;
            }
        }
        return countMale;
    }

    public Integer countFemale(){
        Integer countFemale = 0;
        for(Cattle cattle : cattleRepository.findAll()){
            if(cattle.getGender() == Gender.female){
                countFemale++;
            }
        }
        return countFemale;
    }

    public Cattle validateBreastFeeding(Cattle cattle) {
        if (ChronoUnit.DAYS.between(cattle.getBornAt(), LocalDate.now()) <= 40) {
            cattle.setBreastFeeding(true);
        }
        return cattle;
    }

    public Boolean canBreed(Cattle cattle) {
        return cattle.getStatus() == CattleStatus.cria && ChronoUnit.DAYS.between(cattle.getLastBreeding(), LocalDate.now()) <= 45;
    }

    public void validateBreed(Cattle cattle) {
        if (cattle.getStatus() == CattleStatus.cria)
            cattle.setLastBreeding(LocalDate.now());
    }
}