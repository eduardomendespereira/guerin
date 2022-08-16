package br.com.guerin.Service;


import br.com.guerin.Entity.Farm;
import br.com.guerin.Service.IService.IFarmService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FarmServiceTest {
    private Farm farmFactory() {
        Farm farm = new Farm();
        farm.setName("Guerin");
        farm.setAddress("Estrada Rural, 123");
        return farm;
    }

    private final Farm farm = this.farmFactory();

    @Autowired
    IFarmService farmService;

    @Test
    public void saveFarmTest() {
        this.farmService.save(this.farm);
    }
}
