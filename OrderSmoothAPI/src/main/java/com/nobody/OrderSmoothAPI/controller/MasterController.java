package com.nobody.OrderSmoothAPI.controller;

import com.nobody.OrderSmoothAPI.entity.Language;
import com.nobody.OrderSmoothAPI.entity.RestaurantGenre;
import com.nobody.OrderSmoothAPI.service.LanguageService;
import com.nobody.OrderSmoothAPI.service.RestaurantGenreService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/master")
public class MasterController {

  private final LanguageService languageService;

  private final RestaurantGenreService restaurantGenreService;

  public MasterController(
    LanguageService languageService,
    RestaurantGenreService restaurantGenreService
  ) {
    this.languageService = languageService;
    this.restaurantGenreService = restaurantGenreService;
  }

  @GetMapping("/language")
  public ResponseEntity<List<Language>> getLanguage() {
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(languageService.getAllLanguage());
  }

  @GetMapping("/restaurant-genre/{langCode}")
  public ResponseEntity<List<RestaurantGenre>> getRestaurantGenre(
    @PathVariable String langCode
  ) {
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(restaurantGenreService.getRestaurantGenre(langCode));
  }
}
