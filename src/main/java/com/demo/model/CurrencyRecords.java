package com.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name="currency_records")
@Data @AllArgsConstructor @NoArgsConstructor
public class CurrencyRecords {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private Long amount;
    private String US;
    @Column(name="inr")
    private String IN;
    private String UK;
    private String CHINA;
    private String JAPAN;
    private String FRANCE;
    private String CANADA;
    private LocalDate date;


}
