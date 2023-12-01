package com.nobody.OrderSmoothAPI.service;

import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.nobody.OrderSmoothAPI.dto.DayInWeekDTO;
import com.nobody.OrderSmoothAPI.entity.DayInWeek;
import com.nobody.OrderSmoothAPI.entity.DayInWeekI18n;
import com.nobody.OrderSmoothAPI.mapper.DayInWeekMapper;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DayInWeekService {

  private final DayInWeekMapper dayInWeekMapper;

  public DayInWeekService(DayInWeekMapper dayInWeekMapper) {
    this.dayInWeekMapper = dayInWeekMapper;
  }

  public List<DayInWeekDTO> getDayInWeek(String langCode) {
    return dayInWeekMapper.selectJoinList(
      DayInWeekDTO.class,
      new MPJLambdaWrapper<DayInWeek>()
        .select(DayInWeek::getDayInWeekId)
        .selectCollection(DayInWeekI18n.class, DayInWeekDTO::getI18n)
        .leftJoin(
          DayInWeekI18n.class,
          DayInWeekI18n::getDayInWeekId,
          DayInWeek::getDayInWeekId
        )
        .eq(DayInWeekI18n::getLangCode, langCode)
        .eq(DayInWeek::getIsInvalid, false)
        .eq(DayInWeek::getIsDeleted, false)
        .eq(DayInWeekI18n::getIsInvalid, false)
        .eq(DayInWeekI18n::getIsDeleted, false)
    );
  }
}
