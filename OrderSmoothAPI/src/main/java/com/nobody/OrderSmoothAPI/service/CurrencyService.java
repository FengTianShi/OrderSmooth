package com.nobody.OrderSmoothAPI.service;

import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.nobody.OrderSmoothAPI.entity.Currency;
import com.nobody.OrderSmoothAPI.mapper.CurrencyMapper;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CurrencyService {

  private final CurrencyMapper currencyMapper;

  public CurrencyService(CurrencyMapper currencyMapper) {
    this.currencyMapper = currencyMapper;
  }

  public List<Currency> getCurrency() {
    return currencyMapper.selectList(
      new MPJLambdaWrapper<Currency>()
        .select(
          Currency::getCurrencyId,
          Currency::getCurrencyCode,
          Currency::getCurrencySymbol,
          Currency::getCurrencyName
        )
        .eq(Currency::getIsInvalid, false)
        .eq(Currency::getIsDeleted, false)
    );
  }
}
