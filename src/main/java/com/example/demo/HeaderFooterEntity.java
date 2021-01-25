package com.example.demo;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@Builder
public class HeaderFooterEntity {
    @Id
    private Integer id;
    private String headerType;
    private String footerName;
}
