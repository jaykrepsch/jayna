package me.jayna.repository;

import me.jayna.domain.RealEstate;
import me.jayna.domain.RealEstateRealEstate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RealEstateRealEstateRepository extends JpaRepository<RealEstateRealEstate, Long> {
    List<RealEstateRealEstate> findAllByRealestate(RealEstate realEstate);
    List<RealEstateRealEstate> findAllByRelatedRealEstate(RealEstate realEstate);
    void deleteAllByRealestate(RealEstate realEstate);
    void deleteAllByRelatedRealEstate(RealEstate realEstate);
} 