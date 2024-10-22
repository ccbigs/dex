package com.dexBusman.serviceImpl;

import com.dexBusman.dao.WaresDao;
import com.dexBusman.service.WaresService;
import domain.ordersPojo;
import domain.warePojo;
import org.springframework.stereotype.Service;
import qo.ware_orderPojo;
import utils.JsonObject;
import utils.getTimeNow;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-11-时间:19:39
 * @message:
 */
@Service
public class WaresServiceImpl implements WaresService
{

    @Resource
    private WaresDao waredao;

    @Override
    public JsonObject busmanAddWare(warePojo ware) {
        ware.setWare_date(new getTimeNow().getTimeNowYMDHMS());
        JsonObject addJson = new JsonObject();
        if (waredao.busmanAddWare(ware) > 0){
            addJson.setCode(200);
        }else {
            addJson.setCode(500);
        }
        return addJson;
    }

    @Override
    public JsonObject getWareByBusman(int busman_id) {
        JsonObject gwbbJson = new JsonObject();

        List<ware_orderPojo> wareByBusman = waredao.getWareByBusman(busman_id);
        List<ordersPojo> wareTotalPriceByBusman = waredao.getWareTotalPriceByBusman(busman_id);


        for (ware_orderPojo ware : wareByBusman) {
            ware.setTotalSale(0.0);
            for (ordersPojo order : wareTotalPriceByBusman) {
                if (ware.getId() == order.getWare_id()){
                    ware.setTotalSale(order.getOrders_totalprice().doubleValue());
                }
            }
        }

        gwbbJson.setCode(200);
        gwbbJson.setObject(wareByBusman);
        gwbbJson.setSize(wareByBusman.size());
        return gwbbJson;
    }

    @Override
    public JsonObject busmanUpdateWareMessage(warePojo ware) {
        JsonObject updateJson = new JsonObject();
        if (waredao.busmanUpdateWareMessage(ware) > 0){
            updateJson.setCode(200);
        }else {
            updateJson.setCode(500);
        }
        return updateJson;
    }

    @Override
    public JsonObject busmanUpdateWareNumber(int id, int number) {
        JsonObject updateJson = new JsonObject();
        if (waredao.busmanUpdateWareNumber(id, number) > 0){
            updateJson.setCode(200);
        }else {
            updateJson.setCode(500);
        }
        return updateJson;
    }

    @Override
    public JsonObject busmanUnderCarriageWare(int id) {
        JsonObject UCJson = new JsonObject();
        if (waredao.busmanUnderCarriageWare(id) > 0){
            UCJson.setCode(200);
        }else {
            UCJson.setCode(500);
        }
        return UCJson;
    }

    @Override
    public JsonObject busmanGroundWare(int id) {
        JsonObject GWJson = new JsonObject();
        if (waredao.busmanGroundWare(id) > 0){
            GWJson.setCode(200);
        }else {
            GWJson.setCode(500);
        }
        return GWJson;
    }

    @Override
    public JsonObject busmanAlreadyGroundWare(int busman_id) {
        JsonObject jsonObject = new JsonObject();
        List<warePojo> warePojos = waredao.busmanAlreadyGroundWare(busman_id);
        System.out.println(warePojos.toString());
        if (warePojos == null){
            jsonObject.setCode(500);
        }else {
            jsonObject.setCode(200);
            jsonObject.setSize(warePojos.size());
            jsonObject.setObject(warePojos);
        }
        return jsonObject;
    }

    /*商家删除商品*/
    @Override
    public int busmanDeleteWare(int busmanId, int wareId) {

        /*删除轮播图的*/
        waredao.busmanDeleteWareToCarousel(wareId);
        /*删除推荐商位的*/
        waredao.busmanDeleteWareToRecommend(wareId);
        /*删除购物车的*/
        waredao.busmanDeleteWareToCart(wareId);
        /*删除商品的全部规格信息*/
        waredao.busmanDeleteWareToStandard(wareId);

        return waredao.busmanDeleteWare(busmanId, wareId);

    }
}
