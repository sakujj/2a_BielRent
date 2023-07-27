package by.fpmibsu.bielrent.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UriPatterns {
    public static final String HOME = "/";
    public static final String CREATE_LISTING = "/" + HtmlPages.CREATE_LISTING_PAGE;
    public static final String IMAGES = "/images";
    public static final String LOGIN = "/" + HtmlPages.LOGIN_PAGE;
    public static final String REGISTRATION = "/" + HtmlPages.REGISTRATION_PAGE;
    public static final String LISTING_PAGE = "/" + HtmlPages.LISTING_PAGE;
    public static final String ERROR_PAGE = "/" + HtmlPages.ERROR_PAGE;
    public static final String GET_USERS_REST_API = "/api/users";
    public static final String ROOT = "/app";
    public static final String PROFILE_PAGE = "/" + HtmlPages.PROFILE_PAGE;
}
