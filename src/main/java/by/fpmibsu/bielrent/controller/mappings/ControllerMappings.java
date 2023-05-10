package by.fpmibsu.bielrent.controller.mappings;

import by.fpmibsu.bielrent.controller.webcontroller.*;
import by.fpmibsu.bielrent.constants.UriPatterns;
import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class ControllerMappings {

    private static Map<String, Controller> controllersByURL;


    static {
        controllersByURL = new HashMap<>();
        controllersByURL.put(UriPatterns.HOME, new HomeController());
        controllersByURL.put(UriPatterns.LOGIN, new LoginController());
        controllersByURL.put(UriPatterns.REGISTRATION, new RegistrationController());
        controllersByURL.put(UriPatterns.CREATE_LISTING, new CreateListingController());
        controllersByURL.put(UriPatterns.IMAGES, new ImagesController());
    }



    public static Controller resolveControllerByPath(String uri) {
        Controller controller = controllersByURL.get(uri);
        if (controller != null)
            return controller;
        else if (uri.startsWith(UriPatterns.IMAGES)) {
            return controllersByURL.get(UriPatterns.IMAGES);
        }

        return null;
    }

}
