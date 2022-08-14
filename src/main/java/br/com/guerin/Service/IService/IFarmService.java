package br.com.guerin.Service.IService;

import br.com.guerin.Entity.Farm;

public interface IFarmService {
    void inactivate(Long id, Farm farm);
    Farm findByName(String name);
}
