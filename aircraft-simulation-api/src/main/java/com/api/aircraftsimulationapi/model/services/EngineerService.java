package com.api.aircraftsimulationapi.model.services;

import com.api.aircraftsimulationapi.model.entities.Engineer;
import com.api.aircraftsimulationapi.model.repositories.EngineerRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class EngineerService {
    private final EngineerRepository engineerRepository;

    public EngineerService(EngineerRepository engineerRepository) {
        this.engineerRepository = engineerRepository;
    }

    @Transactional
    public Engineer save(Engineer engineer) {
        return engineerRepository.save(engineer);
    }

    public boolean existsByCPF(String cpf) {
        return engineerRepository.existsById(cpf);
    }

    public List<Engineer> getAllEnginneers() {
        return engineerRepository.findAll();
    }

    public Optional<Engineer> findByCPF(String cpf) {
        return engineerRepository.findById(cpf);
    }

    @Transactional
    public void delete(Engineer engineer) {
        engineerRepository.delete(engineer);
    }
}
