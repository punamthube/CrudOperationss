package com.CrudOperations.CrudOperations.exception;

public enum ErrorCode {

    RecordNotFound(404),
    Error(400),
    InvalidParameter(7),
    RequiredVariableIsNull(406),
    RecordAlreadyExist(405),
    UnderMaintenance(1000),
    UnSufficientBalance(406),

    ;

    int code;

    ErrorCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
