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
        parameter.setIncluded(LocalDateTime.now());
        return parameter;
    }

    private final Parameter parameter = this.parameterFactory();

    @Test
    public void save() {
        var obj = parameterService.save(parameter);
        Assertions.assertEquals(obj.getId(), parameter.getId());
        Assertions.assertEquals(obj.getValue(), parameter.getValue());
        Assertions.assertEquals(obj.getDescription(), parameter.getDescription());
    }

    @Test
    public void update() {
        var p = parameterService.save(parameter);
        var updated_parameter = parameterService.findById(parameter.getId());
        updated_parameter.get().setValue("Nao");
        var obj = parameterService.save(updated_parameter.get());
        Assertions.assertEquals(obj.getValue(), updated_parameter.get().getValue());
        Assertions.assertEquals(obj.getId(), updated_parameter.get().getId());
    }
    @Test
    public void findById() {
        var p = parameterService.save(parameter);
        var u = parameterService.findById(parameter.getId());
        Assertions.assertEquals(u.get().getId(), u.get().getId());
    }
    @Test
    public void delete() {
        var p = parameterService.save(parameter);
        parameterService.delete(parameter.getId());
        Assertions.assertEquals(false, parameterService.findById(parameter.getId()).isPresent());
    }
}
