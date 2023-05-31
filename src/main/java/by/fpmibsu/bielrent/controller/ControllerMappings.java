package by.fpmibsu.bielrent.controller;

import by.fpmibsu.bielrent.controller.webcontroller.*;
import by.fpmibsu.bielrent.constants.UriPatterns;
import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class ControllerMappings {

    private static Map<String, Controller> controllersByUri;


    static {
        controllersByUri = new HashMap<>();
        controllersByUri.put(UriPatterns.HOME, new HomeController());
        controllersByUri.put(UriPatterns.LOGIN, new LoginController());
        controllersByUri.put(UriPatterns.REGISTRATION, new RegistrationController());
        controllersByUri.put(UriPatterns.CREATE_LISTING, new CreateListingController());
        controllersByUri.put(UriPatterns.IMAGES, new ImagesController());
        controllersByUri.put(UriPatterns.LISTING_PAGE, new ListingPageController());
        controllersByUri.put(UriPatterns.ERROR_PAGE, new ErrorPageController());
    }



    public static Controller resolveControllerByPath(String uri) {
        Controller controller = controllersByUri.get(uri);
        if (controller != null)
            return controller;
        else if (uri.startsWith(UriPatterns.IMAGES)) {
            return controllersByUri.get(UriPatterns.IMAGES);
        }

        return null;
    }

}
