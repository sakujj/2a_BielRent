package by.fpmibsu.bielrent.controller.mappings;

import by.fpmibsu.bielrent.controller.webcontroller.*;
import by.fpmibsu.bielrent.constants.UriPatterns;
import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class ControllerMappings {

    private static Map<String, Controller> controllersByURI;


    static {
        controllersByURI = new HashMap<>();
        controllersByURI.put(UriPatterns.HOME, new HomeController());
        controllersByURI.put(UriPatterns.LOGIN, new LoginController());
        controllersByURI.put(UriPatterns.REGISTRATION, new RegistrationController());
        controllersByURI.put(UriPatterns.CREATE_LISTING, new CreateListingController());
        controllersByURI.put(UriPatterns.IMAGES, new ImagesController());
        controllersByURI.put(UriPatterns.LISTING_PAGE, new ListingPageController());
    }



    public static Controller resolveControllerByPath(String uri) {
        Controller controller = controllersByURI.get(uri);
        if (controller != null)
            return controller;
        else if (uri.startsWith(UriPatterns.IMAGES)) {
            return controllersByURI.get(UriPatterns.IMAGES);
        }

        return null;
    }

}
