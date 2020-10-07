package mvi.accounts.service.dto;

import lombok.Data;

@Data
public class ErrorDto {
    private String origin;
    private String message;
    private String scope;
}
