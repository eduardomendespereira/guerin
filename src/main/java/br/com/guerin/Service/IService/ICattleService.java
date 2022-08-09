package br.com.guerin.Service.IService;

import br.com.guerin.Entity.Cattle;

public interface ICattleService {
    void inactivate(Long id, Cattle cattle);
}
