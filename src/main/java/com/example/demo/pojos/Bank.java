package com.example.demo.pojos;

import lombok.Getter;
import lombok.Setter;

public class Bank {
    @Getter @Setter private Integer id;
    @Getter @Setter private String bankName;

    Bank(Integer id, String bankName){
        super();
        this.id = id;
        this.bankName = bankName;
    }
    @Override
    public String toString() {
        return "Bank [id=" + id + ", bankName=" + bankName + "]";
    }
}