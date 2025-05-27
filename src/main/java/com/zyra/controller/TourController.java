package com.zyra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.zyra.dto.TourDTO;
import com.zyra.service.TourService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/tours")
public class TourController {

    @Autowired
    private TourService tourService;

    @PostMapping
    public ResponseEntity<TourDTO> createTour(@Valid @RequestBody TourDTO tourDTO) {
        return new ResponseEntity<>(tourService.createTour(tourDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TourDTO>> getAllTours() {
        return new ResponseEntity<>(tourService.getAllTours(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TourDTO> getTourById(@PathVariable Long id) {
        return new ResponseEntity<>(tourService.getTourById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TourDTO> updateTour(@PathVariable Long id, @Valid @RequestBody TourDTO tourDTO) {
        return new ResponseEntity<>(tourService.updateTour(id, tourDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTour(@PathVariable Long id) {
        tourService.deleteTour(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}