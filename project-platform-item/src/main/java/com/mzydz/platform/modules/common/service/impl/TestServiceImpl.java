package com.mzydz.platform.modules.common.service.impl;

import com.mzydz.platform.modules.common.service.TestService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author kongling
 * @package com.mzydz.platform.modules.common.service.impl
 * @date 2019-09-02  17:22
 * @project ad-publish-cloud
 */
@Service
public class TestServiceImpl implements TestService {
    @Override
    @Async("taskExecutor")
    public void test1() throws InterruptedException {
        Thread.sleep(10000);
        System.out.println("1--------------------------------------------");
    }

    @Override
    public void test2() {
        System.out.println("2--------------------------------------------");
    }
}
