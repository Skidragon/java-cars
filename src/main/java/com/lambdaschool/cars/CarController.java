package com.lambdaschool.cars;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class CarController {
    private final CarRepository carRepo;
    private final RabbitTemplate rt;

    public CarController(CarRepository carRepo, RabbitTemplate rt) {
        this.carRepo = carRepo;
        this.rt = rt;
    }

    @GetMapping("/cars")
    public List<Car> all() {
        return carRepo.findAll();
    }
    @PostMapping("/cars/upload")
    public List<Car> uploadCars(@RequestBody List<Car> newCars) {
        return carRepo.saveAll(newCars);
    }
}
