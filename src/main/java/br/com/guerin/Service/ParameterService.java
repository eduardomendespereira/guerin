package br.com.guerin.Service;

import br.com.guerin.Entity.Parameter;
import br.com.guerin.Entity.User;
import br.com.guerin.Repository.Parameter.ParameterRepository;
import br.com.guerin.Service.IService.IParameterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ParameterService implements IParameterService {
    private final ParameterRepository parameterRepository;

    @Transactional
    public Parameter save(Parameter parameter) {
        if (findById(parameter.getId()).isPresent() && parameter.getIncluded() == null)
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Parametro ja cadastrado!");
        if (parameter.getIncluded() == null)
            parameter.setIncluded(LocalDateTime.now());
        return parameterRepository.save(parameter);
    }

    public Page<Parameter> findAll(Pageable pageable) {
        return parameterRepository.findAll(pageable);
    }

    public Optional<Parameter> findById(String id) {
        return parameterRepository.findById(id);
    }

    @Transactional
    public void delete(String id) {
        if (parameterRepository.findById(id).isPresent()) {
            parameterRepository.deleteById(id);
        }
    }
}
