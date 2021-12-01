package wcs.cerebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import wcs.cerebook.repository.CartographyRepository;

@Controller
public class CartographyController {
    @Autowired
    private CartographyRepository cartographyRepository;


}
