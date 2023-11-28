package com.nobody.OrderSmoothAPI.service;

import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.nobody.OrderSmoothAPI.dto.PayMethodDTO;
import com.nobody.OrderSmoothAPI.entity.PayMethod;
import com.nobody.OrderSmoothAPI.entity.PayMethodI18n;
import com.nobody.OrderSmoothAPI.mapper.PayMethodMapper;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PayMethodService {

  private final PayMethodMapper payMethodMapper;

  public PayMethodService(PayMethodMapper payMethodMapper) {
    this.payMethodMapper = payMethodMapper;
  }

  public List<PayMethodDTO> getPayMethod(String langCode) {
    return payMethodMapper.selectJoinList(
      PayMethodDTO.class,
      new MPJLambdaWrapper<PayMethod>()
        .select(PayMethod::getPayMethodId)
        .selectCollection(PayMethodI18n.class, PayMethodDTO::getI18n)
        .leftJoin(
          PayMethodI18n.class,
          PayMethodI18n::getPayMethodId,
          PayMethod::getPayMethodId
        )
        .eq(PayMethodI18n::getLangCode, langCode)
        .eq(PayMethod::getIsInvalid, false)
        .eq(PayMethod::getIsDeleted, false)
        .eq(PayMethodI18n::getIsInvalid, false)
        .eq(PayMethodI18n::getIsDeleted, false)
    );
  }
}
