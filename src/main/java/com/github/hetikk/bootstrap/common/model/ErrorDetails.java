package com.github.hetikk.bootstrap.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDetails {

    public String message;
    public Throwable cause;

    public static ErrorDetails from(String message) {
        return new ErrorDetails(message, null);
    }

}
