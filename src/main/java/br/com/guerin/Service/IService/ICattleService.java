package br.com.guerin.Service.IService;

import br.com.guerin.Entity.Cattle;
import br.com.guerin.Payload.Cattle.ResultGetFathers;
import br.com.guerin.Payload.Cattle.ResultGetSons;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Optional;

public interface ICattleService {
    Cattle update(Long id, Cattle cattle);
    Page<Cattle> findAll(Pageable pageable);
    Optional<Cattle> findById(Long id);
    Cattle save(Cattle cattle);
    void inactivate(Long id, Cattle cattle);
    Cattle findByEarring(Long earring);
    Cattle findByEarringOrNew(Long earring);
    ResultGetFathers getFathers(Long earring);
    ResultGetSons getSons(Long earring);
    ArrayList<Cattle> findBySpecie(Long specie_id);
    ArrayList<Cattle> findByFarm(Long farm_id);
}
