package br.com.guerin.Service.IService;

import br.com.guerin.Entity.Vaccine;
import br.com.guerin.Repository.User.VaccineRepository;
import org.springframework.beans.factory.annotation.Autowired;

public interface IVaccineService {
    void disable(Long id, Vaccine vaccine);
}
