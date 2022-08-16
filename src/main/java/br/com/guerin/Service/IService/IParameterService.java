package br.com.guerin.Service.IService;

import br.com.guerin.Entity.Parameter;
import br.com.guerin.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IParameterService {
    Parameter save(Parameter user);
    Page<Parameter> findAll(Pageable pageable);
    Optional<Parameter> findById(String id);
    void delete (String id);
}
