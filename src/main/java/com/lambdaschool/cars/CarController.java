package com.lambdaschool.cars;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    @GetMapping("/cars/id/{id}")
    public Car getCarById(@PathVariable Long id) {
        return carRepo.findById(id)
                .orElseThrow(() -> new CarNotFoundException(id));
    }

    @GetMapping("/cars/year/{year}")
    public List<Car> getCarsByYearModel(@PathVariable int year) {
        return carRepo.findAll()
                .stream()
                .filter(car -> car.getYear() == year)
                .collect(Collectors.toList());
    }

    @GetMapping("/cars/brand/{brand}")
    public List<Car> getCarsByBrand(@PathVariable String brand) {

        CarLog message = new CarLog("Search for " + brand);
        log.info("Search for " + brand);

        return carRepo.findAll()
                .stream()
                .filter(car -> car.getBrand().equalsIgnoreCase(brand))
                .collect(Collectors.toList());
    }

    @PostMapping("/cars/upload")
    public List<Car> uploadCars(@RequestBody List<Car> newCars) {
        return carRepo.saveAll(newCars);
    }
}
