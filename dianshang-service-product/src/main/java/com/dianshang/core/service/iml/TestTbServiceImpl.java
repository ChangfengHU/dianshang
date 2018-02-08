package com.dianshang.core.service.iml;

import com.dianshang.core.dao.TestTbDAO;
import com.dianshang.core.pojo.TestTb;
import com.dianshang.core.service.TestTbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 25131 on 2018/2/6.
 */
@Service("testTbService")
public class TestTbServiceImpl implements TestTbService {
    @Autowired
    private TestTbDAO testTbDAO;
    @Override
    public void add(TestTb testTb) {
        testTbDAO.add1(testTb);
    }
}
