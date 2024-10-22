package com.dexUser.controller;

import com.dexUser.service.BonusService;
import domain.bonusPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import qo.BonusBonusOrderPojo;
import utils.JsonObject;
import utils.getTimeNow;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/bonus")
@CrossOrigin
public class BonusController {

    @Autowired
    private BonusService bonusService;

    /*1.用户点击直播带货商品生成链接*/
    @PostMapping("/userAttendBonusByWare")
    JsonObject userAttendBonusByWare(bonusPojo bonus){
        JsonObject jsonObject = new JsonObject();

        /*这里的id是前端传输过来的分钟*/
        bonus.setEnddate(getTimeNow.getMinuteAddYMDHMS(bonus.getId()));
        bonus.setBegindate(new getTimeNow().getTimeNowYMDHMS());

        int result=bonusService.userAttendBonusByWare(bonus);

        if (result == 500){
            jsonObject.setCode(500);
        }else {
            /*链接请求成功，将mysql中的商品链接和秒杀信息存入redis数据库*/
            if (bonusService.successfulParticipationInSecondKill(bonus.getWare_id(), result) == 200){
                jsonObject.setCode(200);
                jsonObject.setObject(result);
                jsonObject.setMessage("http://localhost:8080/dexSeckill/"+result);
            }else {
                jsonObject.setCode(500);
                jsonObject.setMessage("redis存入数据出错");
            }
        }

        return jsonObject;
    }

    /*用户请求秒杀结果数据，返回直播数据和分红*/
    @PostMapping("getUserBonusDate")
    JsonObject getUserBonusDate(int user_id) throws ParseException {
        JsonObject jsonObject = new JsonObject();
        List<BonusBonusOrderPojo> userBonusDate = bonusService.getUserBonusDate(user_id);

        if (userBonusDate.size() > 0){
            jsonObject.setCode(200);
            jsonObject.setSize(userBonusDate.size());
            jsonObject.setObject(userBonusDate);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }

    /*返回用户请求的秒杀全部收益和实际收益*/
    @PostMapping("/getUserProfit")
    JsonObject getUserProfit(int user_id){
        JsonObject jsonObject = new JsonObject();

        BigDecimal[] userProfit = bonusService.getUserProfit(user_id);

        jsonObject.setCode(200);
        jsonObject.setObject(userProfit);

        return jsonObject;
    }

}
