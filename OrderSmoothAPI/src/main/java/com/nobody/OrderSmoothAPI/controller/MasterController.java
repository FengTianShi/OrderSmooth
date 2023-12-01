package com.nobody.OrderSmoothAPI.controller;

import com.nobody.OrderSmoothAPI.dto.DayInWeekDTO;
import com.nobody.OrderSmoothAPI.dto.PayMethodDTO;
import com.nobody.OrderSmoothAPI.dto.RestaurantGenreDTO;
import com.nobody.OrderSmoothAPI.entity.Currency;
import com.nobody.OrderSmoothAPI.entity.Language;
import com.nobody.OrderSmoothAPI.service.CurrencyService;
import com.nobody.OrderSmoothAPI.service.DayInWeekService;
import com.nobody.OrderSmoothAPI.service.LanguageService;
import com.nobody.OrderSmoothAPI.service.PayMethodService;
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

  private final CurrencyService currencyService;

  private final PayMethodService payMethodService;

  private final DayInWeekService dayInWeekService;

  private final RestaurantGenreService restaurantGenreService;

  public MasterController(
    LanguageService languageService,
    CurrencyService currencyService,
    PayMethodService payMethodService,
    DayInWeekService dayInWeekService,
    RestaurantGenreService restaurantGenreService
  ) {
    this.languageService = languageService;
    this.currencyService = currencyService;
    this.payMethodService = payMethodService;
    this.dayInWeekService = dayInWeekService;
    this.restaurantGenreService = restaurantGenreService;
  }

  @GetMapping("/language")
  public ResponseEntity<List<Language>> getLanguage() {
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(languageService.getLanguage());
  }

  @GetMapping("/currency")
  public ResponseEntity<List<Currency>> getCurrency() {
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(currencyService.getCurrency());
  }

  @GetMapping("/pay-method/{langCode}")
  public ResponseEntity<List<PayMethodDTO>> getPayMethod(
    @PathVariable String langCode
  ) {
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(payMethodService.getPayMethod(langCode));
  }

  @GetMapping("/day-in-week/{langCode}")
  public ResponseEntity<List<DayInWeekDTO>> getDayInWeek(
    @PathVariable String langCode
  ) {
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(dayInWeekService.getDayInWeek(langCode));
  }

  @GetMapping("/restaurant-genre/{langCode}")
  public ResponseEntity<List<RestaurantGenreDTO>> getRestaurantGenre(
    @PathVariable String langCode
  ) {
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(restaurantGenreService.getRestaurantGenre(langCode));
  }
}
