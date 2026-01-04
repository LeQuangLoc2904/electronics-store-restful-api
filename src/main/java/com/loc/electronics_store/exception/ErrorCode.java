package com.loc.electronics_store.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    // validate product
    PRODUCTNAME_NOTEMPTY("*product name not empty", 1001, HttpStatus.BAD_REQUEST),
    PRODUCTPRICE_NOTEMPTY("*product price not empty", 1002, HttpStatus.BAD_REQUEST),
    PRODUCTPRICE_INVALID("*product price invalid", 1003, HttpStatus.BAD_REQUEST),
    STOCKQUANTITY_NOTEMPTY("*stock quantity not empty", 1004, HttpStatus.BAD_REQUEST),
    STOCKQUANTITY_INVALID("*stock quantity invalid", 1005, HttpStatus.BAD_REQUEST),
    IMAGEURLS_NOTEMPTY("*image must have at least one", 1006, HttpStatus.BAD_REQUEST),
    ATTRIBUTENAME_NOTEMPTY("*attribute name not empty", 1007, HttpStatus.BAD_REQUEST),
    ATTRIBUTEVALUE_NOTEMPTY("*attribute value not empty", 1008,  HttpStatus.BAD_REQUEST),
    THUMBNAIL_NOTEMPTY("*thumbnail not empty", 1009, HttpStatus.BAD_REQUEST),
    DESCRIPTION_NOTEMPTY("*description not empty", 1010, HttpStatus.BAD_REQUEST),
    PRODUCT_NOTEXISTED("product not existed", 1011, HttpStatus.NOT_FOUND),

    // validate user
    USERNAME_NOT_EMPTY("*username not empty", 2001, HttpStatus.BAD_REQUEST),
    PASSWORD_NOT_EMPTY("*password not empty", 2002, HttpStatus.BAD_REQUEST),
    FULLNAME_NOT_EMPTY("*full name not empty", 2003, HttpStatus.BAD_REQUEST),
    USER_EXISTED("*user existed", 2004, HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED("*user not existed", 2005, HttpStatus.NOT_FOUND),

    UNAUTHENTICATED("unauthenticated", 3000, HttpStatus.UNAUTHORIZED),
    CATEGORY_NOTEXISTED("category not existed", 1012, HttpStatus.NOT_FOUND),
    BRAND_NOTEXISTED("brand not existed", 1013, HttpStatus.NOT_FOUND),
    BAD_REQUEST("Bad Request", 400, HttpStatus.BAD_REQUEST),
    UNAUTHORIZED("You do not have permission", 3001, HttpStatus.FORBIDDEN);

    private String message;
    private int code;
    private HttpStatusCode statusCode;

    ErrorCode(String message, int code, HttpStatusCode statusCode) {
        this.message = message;
        this.code = code;
        this.statusCode = statusCode;
    }
}
