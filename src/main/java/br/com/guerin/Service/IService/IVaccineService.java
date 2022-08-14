package br.com.guerin.Service.IService;

import br.com.guerin.Entity.Vaccine;


public interface IVaccineService{
    void disable(Long id, Vaccine vaccine);
}
