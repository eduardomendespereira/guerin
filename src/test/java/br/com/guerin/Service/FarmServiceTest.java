package br.com.guerin.Service;


import br.com.guerin.Entity.Farm;
import br.com.guerin.Service.IService.IFarmService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


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
        this.farm.setName("save_name");
        this.farm.setAddress("save_address");
        Assertions.assertEquals(this.farmService.save(this.farm), this.farm);
    }

    @Test
    public void updateFarmTest() {
        this.farm.setName("update_name");
        this.farm.setAddress("update_address");
        this.farmService.save(this.farm);

        String newName = "new_update_name";
        Farm farm = this.farmService.findByName("update_name").get();
        farm.setName(newName);
        Assertions.assertEquals(this.farmService.save(farm).getName(), newName);
    }

    @Test
    public void disableFarmTest() {
        this.farm.setName("disable_name");
        this.farm.setAddress("disable_address");
        Farm farm = this.farmService.save(this.farm);
        this.farmService.disable(farm.getId(), farm);
        farm = this.farmService.findByName("disable_name").get();
        Assertions.assertTrue(farm.isInactive());
    }

    @Test
    public void findByNameTest() {
        this.farm.setName("findbyname_name");
        this.farm.setAddress("findbyname_address");
        Farm farm = this.farmService.save(this.farm);
        Farm farm2 = this.farmService.findByName(farm.getName()).get();
        Assertions.assertEquals(farm, farm2);
    }

    @Test
    public void findByAddressTest() {
        this.farm.setName("findbyaddress_name");
        this.farm.setAddress("findbyaddress_address");
        Farm farm = this.farmService.save(this.farm);
        Farm farm2 = this.farmService.findByAddress(farm.getAddress()).get();
        Assertions.assertEquals(farm, farm2);
    }

    @Test
    public void findByIdTest() {
        this.farm.setName("findbyid_name");
        this.farm.setAddress("findbyid_address");
        Farm farm = this.farmService.save(this.farm);
        Farm farm2 = this.farmService.findById(farm.getId()).get();
        Assertions.assertEquals(farm, farm2);
    }

    @Test
    public void findAllTest() {
        this.farm.setName("findall_name");
        this.farm.setAddress("findall_address");
        this.farmService.save(this.farm);
        Integer count = this.farmService.findAll(Pageable.unpaged()).getSize();
        Assertions.assertTrue(count >= 1);
    }
}
