package br.com.guerin.Service.IService;

import br.com.guerin.Entity.Weighing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IWeighingService {
    void disable(Long idWeighing, Weighing weighing);
    Weighing findById(Long id);
    Page<Weighing> listAll(Pageable pageable);
    Weighing save(Weighing weighing);
    Weighing saveTransactional(Weighing weighing);
    Weighing update(Long id, Weighing weighing);
    void validarWeighing(Weighing weighing);
}
