package by.fpmibsu.bielrent.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UriPatterns {
    public static final String HOME = "/";
    public static final String CREATE_LISTING = "/" + HtmlPages.CREATE_LISTING_PAGE.substring(0, HtmlPages.CREATE_LISTING_PAGE.length() - 5);
    public static final String IMAGES = "/images";
    public static final String LOGIN = "/" + HtmlPages.LOGIN_PAGE.substring(0, HtmlPages.LOGIN_PAGE.length() - 5);
    public static final String REGISTRATION = "/" + HtmlPages.REGISTRATION_PAGE.substring(0, HtmlPages.REGISTRATION_PAGE.length() - 5);
    public static final String LISTING_PAGE = "/" + HtmlPages.LISTING_PAGE.substring(0, HtmlPages.LISTING_PAGE.length() - 5);
    public static final String ERROR_PAGE = "/" + HtmlPages.ERROR_PAGE.substring(0, HtmlPages.ERROR_PAGE.length() - 5);
    public static final String GET_USERS_REST_API = "/api/users";
    public static final String ROOT = "/app";
}
