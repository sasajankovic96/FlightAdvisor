package com.sasajankovic.application.controllers;

import com.sasajankovic.application.dtos.CityDto;
import com.sasajankovic.domain.entities.city.CityId;
import com.sasajankovic.domain.ports.in.CreateCityUseCase;
import com.sasajankovic.domain.ports.in.FetchCityByIdUseCase;
import com.sasajankovic.domain.ports.in.ImportDataUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
@PreAuthorize("hasRole('ADMINISTRATOR')")
public class AdminController {
    private final CreateCityUseCase createCityUseCase;
    private final FetchCityByIdUseCase fetchCityByIdUseCase;
    private final ImportDataUseCase importDataUseCase;

    @PostMapping(
            value = "/cities",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createCity(@RequestBody @Valid CityDto city) {
        CityId createdCityId = createCityUseCase.createCity(city.toDomainEntity());
        URI location =
                ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(createdCityId.get())
                        .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "/cities/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CityDto fetchCityById(@PathVariable Long id) {
        return CityDto.fromDomainEntity(fetchCityByIdUseCase.fetchById(new CityId(id)));
    }

    @PostMapping(value = "/data/import")
    public ResponseEntity<?> importData() {
        importDataUseCase.importData();
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
