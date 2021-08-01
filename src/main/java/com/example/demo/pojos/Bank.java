package com.example.demo.pojos;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;

public class Bank {
    @Getter @Setter @CsvBindByName(column="id") private Integer id;
    @Getter @Setter @CsvBindByName(column="name") private String name;

    @Override
    public String toString() {
        return "Bank [id=" + id + ", Name=" + name + "]";
    }
}