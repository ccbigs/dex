package com.dexBusman.serviceImpl;

import com.dexBusman.dao.StandardsDao;
import com.dexBusman.service.StandardsService;
import domain.standardsPojo;
import org.springframework.stereotype.Service;
import qo.StandardWarePojo;
import utils.getTimeNow;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-23-时间:9:43
 * @message:
 */
@Service
public class StandardsServiceImpl implements StandardsService {

    @Resource
    private StandardsDao standardsDao;

    /*根据商家的id返回商品的全部规格信息*/
    @Override
    public List<StandardWarePojo> getStandardByBusman(int busman_id) throws ParseException {
        List<StandardWarePojo> standards = standardsDao.getStandardByBusman(busman_id);

        for (StandardWarePojo standard : standards) {
            standard.setCreate_time(getTimeNow.dataBasicToYMDHMS(standard.getCreate_time()));
        }

        return standards;
    }

    /*商家添加商品规格*/
    @Override
    public int AddStandards(standardsPojo standards) {
        /*更新商品表中的总数信息*/
        standardsDao.updateWareNumber(standards.getStandards_total(),standards.getWare_id());
        /*更新商品（ware）的价格信息*/
        standardsDao.updateWarePrice(standards);
        return standardsDao.AddStandards(standards);
    }

    /*管理员添加自己的商品规格的数量*/
    @Override
    public int busmanAddStandardNumber(int standard_id, int number ,int ware_id) {
        /*更新商品表中的商品数据*/
        standardsDao.updateWareNumber(number,ware_id);
        return standardsDao.busmanAddStandardNumber(standard_id, number);
    }

    /*商家删除选中的商品规格*/
    @Override
    public int deleteStandardByBusman(int standard_id) {
        return standardsDao.deleteStandardByBusman(standard_id);
    }
}
