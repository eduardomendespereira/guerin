package br.com.guerin.Service.IService;

import br.com.guerin.Entity.Weighing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IWeighingService {
    void disable(Long idWeighing);
    void enable(Long idWeighing);
    Weighing findById(Long id);
    Page<Weighing> listAll(Pageable pageable);
    Weighing save(Weighing weighing);
    Weighing update(Long id, Weighing weighing);
    public Integer count();
}
