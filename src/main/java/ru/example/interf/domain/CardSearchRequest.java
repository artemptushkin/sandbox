package ru.example.interf.domain;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

@Value
@Builder
public class CardSearchRequest {
    long id;
}
