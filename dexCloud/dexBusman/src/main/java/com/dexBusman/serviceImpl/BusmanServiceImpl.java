package com.dexBusman.serviceImpl;

import com.dexBusman.dao.BusmanDao;
import com.dexBusman.service.BusmanService;
import domain.busmanPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author:左泽林
 * @date:日期:2021-10-13-时间:11:38
 * @message:
 */
@Service
public class BusmanServiceImpl implements BusmanService {

    @Autowired
    private BusmanDao busmanDao;

    @Override
    public int updateLocal(busmanPojo busman) {
        return busmanDao.updateLocal(busman);
    }

    @Override
    public int updatePassword(int id, String newPass) {
        return busmanDao.updatePassword(id, newPass);
    }
}
