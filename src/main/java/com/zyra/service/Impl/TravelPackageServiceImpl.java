package com.zyra.service.Impl;

import com.zyra.model.TravelPackage;
import com.zyra.repository.TravelPackageRepository;
import com.zyra.service.TravelPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TravelPackageServiceImpl implements TravelPackageService {

    @Autowired
    private TravelPackageRepository travelPackageRepository;

    @Override
    public TravelPackage createPackage(TravelPackage travelPackage) {
        return travelPackageRepository.save(travelPackage);
    }

    @Override
    public Optional<TravelPackage> getPackageById(Long id) {
        return travelPackageRepository.findById(id);
    }

    @Override
    public List<TravelPackage> getAllPackages() {
        return travelPackageRepository.findAll();
    }

    @Override
    public TravelPackage updatePackage(TravelPackage travelPackage) {
        return travelPackageRepository.save(travelPackage);
    }

    @Override
    public void deletePackage(Long id) {
        travelPackageRepository.deleteById(id);
    }
}
