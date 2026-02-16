package com.pragma.usuarios.domain.constants;

public final class DomainConstants {
    private DomainConstants() {
    }

    public static final int MINIMUM_ADULT_AGE = 18;
    public static final int MIN_PASSWORD_LENGTH = 8;
    public static final String DOCUMENT_NUMBER_REGEX = "^[0-9]+$";
    public static final String PHONE_NUMBER_REGEX = "^\\+?\\d{7,13}$";
    public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    public static final Long ADMIN_ID = 1L;
    public static final Long OWNER_ID = 2L;
    public static final Long EMPLOYEE_ID = 3L;
    public static final Long CLIENT_ID = 4L;
}
