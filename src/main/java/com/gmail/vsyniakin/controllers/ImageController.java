package com.gmail.vsyniakin.controllers;

import com.gmail.vsyniakin.model.entity.Recipe;
import com.gmail.vsyniakin.model.enums.TypeRecipe;
import com.gmail.vsyniakin.services.interfaces.RecipeService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class ImageController implements ServletContextAware {

    @Autowired
    private RecipeService recipeService;

    private ServletContext context;

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.context = servletContext;
    }

    @RequestMapping(value = "/recipe/img", method = RequestMethod.GET)
    public ResponseEntity<byte []> imageByIdRecipe(@RequestParam long idr) {
        Recipe recipe = recipeService.getByIdFetchImage(idr);
        if (recipe.getImage() != null) {
            return new ResponseEntity<byte[]>(recipe.getImage().getImage(), HttpStatus.OK);
        } else {
            try {
                InputStream in = context.getResourceAsStream(selectImagePath(recipe.getType()));
                byte[] image = IOUtils.toByteArray(in);
                return new ResponseEntity<byte[]>(image, HttpStatus.OK);
            } catch (IOException e) {
                return new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    private String selectImagePath (TypeRecipe type) {
        switch (type) {
            case SOUP:
                return "/static/img/default soup.png";
            case SALAD:
                return "/static/img/default salad.png";
            case DESSERT:
                return "/static/img/default dessert.png";
            case MAIN_COURSE:
                return "/static/img/default main_course.png";
            default:
                return "/static/img/default other.png";
        }
    }
}
