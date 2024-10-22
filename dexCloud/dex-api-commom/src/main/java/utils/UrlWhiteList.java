package utils;

import java.util.Arrays;

/**
 * @author:左泽林
 * @date:日期:2021-09-06-时间:10:56
 * @message:访问路径白名单类
 */
public class UrlWhiteList {

    private static String[] UrlList=new String[]{
           "/DexAdmin/pictureVerification","/DexAdmin/getUserConfirmPV","/DexAdmin/adminLogin","/DexAdmin/uploadUserHeaderImage",
           "/busman/busmanLogin","/busman/busmanRegister","/pictureVerification/pictureVerification",
           "/pictureVerification/getUserConfirmPV","/pictureVerification/uploadUserHeaderImage",
           "/home/getAllHome","/standards/getStandardByWare",
           "/wares/getWareById","/wares/getWareActive","/wares/getBusmanAddress","/wares/getAllWare",
           "/wares/getAllCategorie","/wares/getWareByActive","/wares/getWareByCategorie","/wares/getPromptWare",
           "/wares/getSearchWares",
           "/user/login","/user/register","/user/uploadUserHeaderImage","/comment/getCommentByWare"
    };

    /*判断传输过来的路径是否在白名单里面，是的话返回true，不是的话返回false*/
    public static boolean JudgeUrlFilter(String VisterUrl){

        for (String s : UrlList) {
            if (s.equals(VisterUrl)){
                return true;
            }
        }
        return false;
    }

}
