package com.dex.serviceImpl;

import com.dex.dao.WareDao;
import com.dex.service.WareService;
import domain.warePojo;
import org.springframework.stereotype.Service;
import utils.getTimeNow;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-10-时间:15:52
 * @message:
 */
@Service
public class WareServiceImpl implements WareService {

    @Resource
    private WareDao wareDao;

    @Override
    public List<warePojo> adminGetAllWare() throws ParseException {
        List<warePojo> warePojos = wareDao.adminGetAllWare();
        for (warePojo warePojo : warePojos) {
            warePojo.setWare_date(getTimeNow.dataBasicToYMDHMS(warePojo.getWare_date()));
        }
        return warePojos;
    }

    @Override
    public int adminModifyWareState(int id, String state) {
        warePojo ware = new warePojo();

        ware.setId(id);
        if ("商品上架".equals(state) || "点击上架".equals(state)){
            ware.setWare_state("售卖中");
        }else if("点击下架".equals(state)){
            ware.setWare_state("已下架");
        }

        return wareDao.adminModifyWareState(ware);
    }

    @Override
    public int adminDeleteWare(int id) {
        return wareDao.adminDeleteWare(id);
    }
}
