package com.loc.electronics_store.exception;

public enum ErrorCode {
    // validate product
    PRODUCTNAME_NOTEMPTY("*product name not empty", 1001),
    PRODUCTPRICE_NOTEMPTY("*product price not empty", 1002),
    PRODUCTPRICE_INVALID("*product price invalid", 1003),
    STOCKQUANTITY_NOTEMPTY("*stock quantity not empty", 1004),
    STOCKQUANTITY_INVALID("*stock quantity invalid", 1005),
    IMAGEURLS_NOTEMPTY("*image must have at least one", 1006),
    ATTRIBUTENAME_NOTEMPTY("*attribute name not empty", 1007),
    ATTRIBUTEVALUE_NOTEMPTY("*attribute value not empty", 1008),
    THUMBNAIL_NOTEMPTY("*thumbnail not empty", 1009),
    DESCRIPTION_NOTEMPTY("*description not empty", 1010),
    PRODUCT_NOTEXISTED("product not existed", 1011),

    CATEGORY_NOTEXISTED("category not existed", 1012),
    BRAND_NOTEXISTED("brand not existed", 1013),
    BAD_REQUEST("Bad Request", 400);

    private String message;
    private int code;

    ErrorCode(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}
