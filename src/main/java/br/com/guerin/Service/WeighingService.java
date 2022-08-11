package br.com.guerin.Service;

import br.com.guerin.Entity.Weighing;
import br.com.guerin.Repository.Weighing.WeighingRepository;
import br.com.guerin.Service.IService.IWeighingService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class WeighingService implements IWeighingService{

    @Autowired
    private WeighingRepository weighingRepository;

    /**
     *
     * @param id
     */
    public Weighing findById(Long id) {
        return this.weighingRepository.findById(id).orElse(new Weighing());
    }

    /**
     *
     * @param pageable
     */
    public Page<Weighing> listAll(Pageable pageable) {
        return this.weighingRepository.findAll(pageable);
    }

    /**
     *
     * @param weighing
     */
    public void insert(Weighing weighing) {
        this.validarWeighing(weighing);
        this.saveTransactional(weighing);
    }

    /**
     *
     * @param weighing
     */
    @Transactional
    public void saveTransactional(Weighing weighing) {
        this.weighingRepository.save(weighing);
    }

    /**
     *
     * @param id
     * @param weighing
     */
    public void update(Long id, Weighing weighing) {
        if (id == weighing.getId()) {
            //this.validarWeighing(weighing);
            this.saveTransactional(weighing);
        }
        else {
            throw new RuntimeException("Error: Não foi possivel editar a Pesagem.");
        }
    }

    /**
     *
     * @param id
     * @param weighing
     */
    @Transactional
    public void disable(Long id, Weighing weighing) {
        if (id == weighing.getId()) {
            this.weighingRepository.disable(weighing.getId());
        }
        else {
            throw new RuntimeException("Error: Não foi possivel desativar a Pesagem.");
        }
    }

    /**
     *
     * @param weighing
     */
    public void validarWeighing(Weighing weighing) {

    }
}
