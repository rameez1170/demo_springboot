package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@Builder
@NoArgsConstructor
public class ItemEntity {
    @Id
    private Integer id;
    private String name;
    private Integer age;
    private Integer height;
    private Integer headerId;
}
