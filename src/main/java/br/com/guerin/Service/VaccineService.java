package br.com.guerin.Service;

import br.com.guerin.Entity.Vaccine;
import br.com.guerin.Repository.User.VaccineRepository;
import br.com.guerin.Service.IService.IVaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * @author Eduardo Mendes
 *
 * @since 1.0.0, 08/08/2022
 * @version 1.0.0
 */
@Service
public class VaccineService implements IVaccineService{

    @Autowired
    private VaccineRepository vaccineRepository;

    public Optional<Vaccine> findById(Long id){
        return this.vaccineRepository.findById(id);
    }

    public Page<Vaccine> findAll(Pageable pageable){
        return this.vaccineRepository.findAll(pageable);
    }

    public void update(Long id, Vaccine vaccine){
       essentialValidation(vaccine);
       saveTransactional(vaccine);
    }

    @Transactional
    public void saveTransactional(Vaccine vaccine){
        this.vaccineRepository.save(vaccine);
    }

    public void insert(Vaccine vaccine){
        essentialValidation(vaccine);
        saveTransactional(vaccine);
    }

    @Transactional
    public void disable(Long id, Vaccine vaccine){
        if(id == vaccine.getId()){
            this.vaccineRepository.disable(vaccine.getId());
        }
    }

    private boolean checkNameIsNull(Vaccine vaccine){
        if(vaccine.getName() == null){
            return true;
        }else{
            return false;
        }
    }

    private boolean checkDateIsNull(Vaccine vaccine){
        if(vaccine.getDate() == null){
            return true;
        }else{
            return false;
        }
    }

    private boolean checkDateIsFuture(Vaccine vaccine){
        if(vaccine.dateIsFuture() == true){
            return true;
        }else{
            return false;
        }
    }

    private void essentialValidation(Vaccine vaccine){
        Assert.isTrue(checkNameIsNull(vaccine), "Erro: Nome da vacina é nulo");
        Assert.isTrue(checkDateIsNull(vaccine), "Erro: Data da vacina é nula");
        Assert.isTrue(checkDateIsFuture(vaccine), "Erro: Data da vacina consta como futura");
    }
}
