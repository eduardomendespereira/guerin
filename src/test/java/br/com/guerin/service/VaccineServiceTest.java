package br.com.guerin.service;

import br.com.guerin.Entity.Vaccine;
import br.com.guerin.Repository.User.VaccineRepository;
import br.com.guerin.Service.VaccineService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.function.BooleanSupplier;

@SpringBootTest
public class VaccineServiceTest {

    @Autowired
    VaccineService vaccineService;
}
