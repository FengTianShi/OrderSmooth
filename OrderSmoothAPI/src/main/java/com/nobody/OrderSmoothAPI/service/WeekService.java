package com.nobody.OrderSmoothAPI.service;

import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.nobody.OrderSmoothAPI.dto.WeekDTO;
import com.nobody.OrderSmoothAPI.entity.Week;
import com.nobody.OrderSmoothAPI.entity.WeekI18n;
import com.nobody.OrderSmoothAPI.mapper.WeekMapper;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class WeekService {

  private final WeekMapper weekMapper;

  public WeekService(WeekMapper weekMapper) {
    this.weekMapper = weekMapper;
  }

  public List<WeekDTO> getWeek(String langCode) {
    return weekMapper.selectJoinList(
      WeekDTO.class,
      new MPJLambdaWrapper<Week>()
        .select(Week::getWeekId)
        .selectCollection(WeekI18n.class, WeekDTO::getI18n)
        .leftJoin(WeekI18n.class, WeekI18n::getWeekId, Week::getWeekId)
        .eq(WeekI18n::getLangCode, langCode)
        .eq(Week::getIsInvalid, false)
        .eq(Week::getIsDeleted, false)
        .eq(WeekI18n::getIsInvalid, false)
        .eq(WeekI18n::getIsDeleted, false)
    );
  }
}
