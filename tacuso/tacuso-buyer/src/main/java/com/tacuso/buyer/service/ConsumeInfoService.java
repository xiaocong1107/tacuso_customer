package com.tacuso.buyer.service;

import com.baomidou.mybatisplus.service.IService;
import com.tacuso.buyer.entity.ConsumeInfo;

public interface ConsumeInfoService extends IService<ConsumeInfo> {
    public ConsumeInfo createOrUpdateConsumeinfo(ConsumeInfo consumeInfo);
}
