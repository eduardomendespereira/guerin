package br.com.guerin.Service.IService;

import br.com.guerin.Entity.Cattle;
import br.com.guerin.Payload.Cattle.ResultFindParents;
import br.com.guerin.Payload.Cattle.ResultFindChildren;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import DTO.Cattle.LactatingCattleDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface ICattleService {
    Cattle update(Long id, Cattle cattle);
    ArrayList<Cattle> findAll();
    Optional<Cattle> findById(Long id);
    Cattle save(Cattle cattle);
    Cattle disable(Long id, Cattle cattle);
    Cattle enable(Long id, Cattle cattle);
    Optional<Cattle> findByEarring(Long earring);
    Cattle findByEarringOrNew(Long earring);
    ResultFindParents findParents(Long earring);
    ResultFindChildren findChildren(Long earring);
    ArrayList<Cattle> findBySpecie(Long specie_id);
    ArrayList<Cattle> findByFarm(Long farm_id);
    void validateParents(Cattle cattle);
    void validateFather(Cattle cattle);
    void validateMother(Cattle cattle);
    Integer count();
    Integer countMale();
    Integer countFemale();
    Cattle validateBreastFeeding(Cattle cattle);
    Boolean canBreed(Long earring);
    List<LactatingCattleDTO> findLactatingCattles();
}
