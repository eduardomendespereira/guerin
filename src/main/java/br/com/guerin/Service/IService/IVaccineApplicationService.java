package br.com.guerin.Service.IService;

import br.com.guerin.Entity.VaccineApplication;

public interface IVaccineApplicationService {
    void disable(Long id, VaccineApplication vaccineApplication);
}
