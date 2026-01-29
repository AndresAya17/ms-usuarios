package com.pragma.usuarios.domain.constants;

public final class DomainConstants {
    private DomainConstants() {
    }

    public static final int MINIMUM_ADULT_AGE = 18;
    public static final int MIN_PASSWORD_LENGTH = 8;
    public static final int MAX_PHONE_LENGTH = 13;
    public static final String DOCUMENT_NUMBER_REGEX = "\\d+";
    public static final String PHONE_NUMBER_REGEX = "^\\+?\\d{7,13}$";
    public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
}
