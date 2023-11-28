package com.nobody.OrderSmoothAPI.service;

import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.nobody.OrderSmoothAPI.entity.Language;
import com.nobody.OrderSmoothAPI.mapper.LanguageMapper;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class LanguageService {

  private final LanguageMapper LanguageMapper;

  public LanguageService(LanguageMapper LanguageMapper) {
    this.LanguageMapper = LanguageMapper;
  }

  public List<Language> getLanguage() {
    return LanguageMapper.selectList(
      new MPJLambdaWrapper<Language>()
        .select(Language::getLangCode, Language::getLangName)
        .eq(Language::getIsInvalid, false)
        .eq(Language::getIsDeleted, false)
    );
  }
}
