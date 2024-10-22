package com.dexStore.serviceImpl;

import com.dexStore.dao.StandardDao;
import com.dexStore.service.StandardService;
import domain.standardsPojo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-23-时间:15:30
 * @message:
 */
@Service
public class StandardServiceImpl implements StandardService {

    @Resource
    private StandardDao standardDao;

    @Override
    public List<standardsPojo> getStandardByWare(int ware_id) {
        return standardDao.getStandardByWare(ware_id);
    }

    @Override
    public int reduceStandardNumberByUserBuyWare(int standard_id, int buy_number) {
        return standardDao.reduceStandardNumberByUserBuyWare(standard_id, buy_number);
    }
}
