package com.demo.repo;

import com.demo.model.CurrencyRecords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<CurrencyRecords, Long> {

}
