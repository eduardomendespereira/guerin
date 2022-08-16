package br.com.guerin.Service;

import br.com.guerin.Entity.Weighing;
import br.com.guerin.Repository.Weighing.WeighingRepository;
import br.com.guerin.Service.IService.IWeighingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author Gabriel Luiz C
 *
 * @since 1.0.0, 08/08/2022
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class WeighingService implements IWeighingService{
    private final WeighingRepository weighingRepository;

    public Weighing findById(Long id) {
        return this.weighingRepository.findById(id).orElse(new Weighing());
    }

    public Page<Weighing> listAll(Pageable pageable) {
        return this.weighingRepository.findAll(pageable);
    }

    public Weighing save(Weighing weighing) {
        return this.saveTransactional(weighing);
    }

    @Transactional
    public Weighing saveTransactional(Weighing weighing) {
        return this.weighingRepository.save(weighing);
    }

    public Weighing update(Long id, Weighing weighing) {
        if (id == weighing.getId()) {
            return this.saveTransactional(weighing);
        }
        else {
            throw new RuntimeException("Error: Não foi possivel editar a Pesagem.");
        }
    }

    @Transactional
    public void disable(Long id, Weighing weighing) {
        if (id == weighing.getId()) {
            this.weighingRepository.disable(weighing.getId());
        }
        else {
            throw new RuntimeException("Error: Não foi possivel desativar a Pesagem.");
        }
    }
}
