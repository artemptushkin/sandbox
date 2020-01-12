package ru.example.interf.domain;

import lombok.Data;

@Data
public class Account {
    private Card card;
    private Amount amount;
}
