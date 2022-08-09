package br.com.guerin.Service;

import br.com.guerin.Entity.EventTimeLine;
import br.com.guerin.Repository.EventTimeLine.EventTLRepository;
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
public class EventTLService {

    @Autowired
    private EventTLRepository eventTLRepository;

    /**
     *
     * @param id
     */
    public EventTimeLine findById(Long id) {
        return this.eventTLRepository.findById(id).orElse(new EventTimeLine());
    }

    /**
     *
     * @param pageable
     */
    public Page<EventTimeLine> listAll(Pageable pageable) {
        return this.eventTLRepository.findAll(pageable);
    }

    /**
     *
     * @param eventTimeLine
     */
    public void insert(EventTimeLine eventTimeLine) {
        this.validarEventTimeLine(eventTimeLine);
        this.saveTransactional(eventTimeLine);
    }

    /**
     *
     * @param eventTimeLine
     */
    @Transactional
    public void saveTransactional(EventTimeLine eventTimeLine) {
        this.eventTLRepository.save(eventTimeLine);
    }

    /**
     *
     * @param id
     * @param eventTimeLine
     */
    public void update(Long id, EventTimeLine eventTimeLine) {
        if (id == eventTimeLine.getId()) {
            this.validarEventTimeLine(eventTimeLine);
            this.saveTransactional(eventTimeLine);
        }
        else {
            throw new RuntimeException("Error: Não foi possivel editar a Pesagem.");
        }
    }

    /**
     *
     * @param id
     * @param eventTimeLine
     */
    @Transactional
    public void disable(Long id, EventTimeLine eventTimeLine) {
        if (id == eventTimeLine.getId()) {
            this.eventTLRepository.disable(eventTimeLine.getId());
        }
        else {
            throw new RuntimeException("Error: Não foi possivel desativar a Pesagem.");
        }
    }

    /**
     *
     * @param eventTimeLine
     */
    public void validarEventTimeLine(EventTimeLine eventTimeLine) {

    }
}
