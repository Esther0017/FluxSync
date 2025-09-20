package com.esther.fluxsync.model.dto;

import com.esther.fluxsync.model.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDTO<T> {

    private Status status;
    private String message;
    private T data;

    public ResponseDTO(Status status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
