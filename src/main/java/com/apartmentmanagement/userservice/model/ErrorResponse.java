package com.apartmentmanagement.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
public class ErrorResponse implements Serializable {
    private String message;
}
