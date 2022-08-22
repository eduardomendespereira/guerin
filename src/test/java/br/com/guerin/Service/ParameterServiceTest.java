package br.com.guerin.Service;

import br.com.guerin.Entity.Parameter;
import br.com.guerin.Entity.Role;
import br.com.guerin.Entity.User;
import br.com.guerin.Service.IService.IParameterService;
import br.com.guerin.Service.IService.IUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootTest
public class ParameterServiceTest {
    @Autowired
    IParameterService parameterService;

    private Parameter parameterFactory() {
        Parameter parameter = new Parameter();
        parameter.setId("PARAMETRO_TESTE");
        parameter.setValue("Sim");
        parameter.setDescription("Parametro de teste");
        return parameter;
    }
    private Parameter parameterNewFactory() {
        Parameter parameter = new Parameter();
        parameter.setId("PARAMETRO_TESTE2");
        parameter.setValue("Nao");
        parameter.setDescription("Parametro de teste 2");
        return parameter;
    }
    private final Parameter parameter = this.parameterFactory();

    @Test
    public void save() {
        var newParam = this.parameterNewFactory();
        var obj = parameterService.save(newParam);
        Assertions.assertEquals(obj.getId(), newParam.getId());
        Assertions.assertEquals(obj.getValue(), newParam.getValue());
        Assertions.assertEquals(obj.getDescription(), newParam.getDescription());
    }

    @Test
    public void saveDuplicated() {
        if (!parameterService.findById(parameter.getId()).isPresent())
            parameterService.save(parameter);
        try {
            var duplicatedParameter = parameterService.save(parameter);
        } catch (ResponseStatusException ex) {
            Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, ex.getStatus());
        }
    }
    @Test
    public void update() {
        if (!parameterService.findById(parameter.getId()).isPresent())
            parameterService.save(parameter);
        var updated_parameter = parameterService.findById(parameter.getId());
        updated_parameter.get().setValue("Nao");
        var obj = parameterService.save(updated_parameter.get());
        Assertions.assertEquals(obj.getValue(), updated_parameter.get().getValue());
        Assertions.assertEquals(obj.getId(), updated_parameter.get().getId());
    }

    @Test
    public void findById() {
        if (!parameterService.findById(parameter.getId()).isPresent())
            parameterService.save(parameter);
        var u = parameterService.findById(parameter.getId());
        Assertions.assertEquals(u.get().getId(), u.get().getId());
    }

    @Test
    public void delete() {
        if (!parameterService.findById(parameter.getId()).isPresent())
            parameterService.save(parameter);
        parameterService.delete(parameter.getId());
        Assertions.assertEquals(false, parameterService.findById(parameter.getId()).isPresent());
    }
}
