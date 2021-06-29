package ru.example.interf.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Amount {
    Long value;
}
