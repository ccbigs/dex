package com.dex.serviceImpl;

import com.dex.dao.BusmanDao;
import com.dex.service.BusmanService;
import domain.busmanPojo;
import org.springframework.stereotype.Service;
import utils.getTimeNow;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-10-时间:15:28
 * @message:
 */
@Service
public class BusmanServiceImpl implements BusmanService {

    @Resource
    private BusmanDao busmanDao;

    @Override
    public List<busmanPojo> adminGetAllBusman() throws ParseException {
        List<busmanPojo> busmanPojos = busmanDao.adminGetAllBusman();
        for (busmanPojo busmanPojo : busmanPojos) {
            busmanPojo.setBus_lasttime(getTimeNow.dataBasicToYMDHMS(busmanPojo.getBus_lasttime()));
        }
        return busmanPojos;
    }

    /*
     * @author: 左先生
     * @date: 2021-07-21 10:22
     * @description:更换账号的状态，封禁和解禁的状态,如果是申请注销，那么就删除该账号
     */
    @Override
    public int EditBusmanState(int id,String busman_state) {
        busmanPojo busmanPojo = new busmanPojo();
        int result=0;

        busmanPojo.setId(id);
        if ("解禁账号".equals(busman_state) || "审核通过".equals(busman_state)){
            busmanPojo.setBus_busman_state("正常");
            result=busmanDao.EditBusmanState(busmanPojo);
        }else if ("封禁账号".equals(busman_state)){
            busmanPojo.setBus_busman_state("封号");
            result=busmanDao.EditBusmanState(busmanPojo);
        }else if ("申请注销".equals(busman_state)){
            result=busmanDao.deleteBusman(id);
        }

        return result;
    }

}
