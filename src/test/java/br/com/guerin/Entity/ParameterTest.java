package br.com.guerin.Entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class ParameterTest {
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
    public void testIfParameterIdIsCorrect() {
        Assertions.assertEquals(this.parameter.getId(), "PARAMETRO_TESTE");
    }
}
