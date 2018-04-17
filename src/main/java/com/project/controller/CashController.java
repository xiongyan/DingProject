package com.project.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.project.model.RespEntity;
import com.project.model.User;
import com.project.service.CashService;
import com.project.util.AlipayConfig;
import com.project.util.CacheUtil;
import com.project.util.DateUtil;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

/**
 * Created by laishun on 2018/3/9.
 */
@RestController
public class CashController {

    @Resource
    private CashService cashService;

    @Resource
    private RespEntity respEntity ;

    /**
     * 查看账户余额
     * @return
     */
    @RequestMapping(value="/account/{userId}",method= RequestMethod.GET)
    public Object queryAccount(@PathVariable String userId){
        return cashService.queryAccount(Integer.parseInt(userId));
    }

    /**
     * 交房租
     * @param req
     * @param houseId
     * @return
     */
    @RequestMapping(value="/pay/{houseId}",method= RequestMethod.GET)
    public Object payRent(HttpServletRequest req,@PathVariable int houseId){
        User user = CacheUtil.getInstance().getUser(req);
        double money = Double.parseDouble(req.getParameter("rent"));
        return cashService.payRent(user.getId(), houseId,money);
    }

    /**
     * 水费查询
     * @param houseId
     * @return
     */
    @RequestMapping(value="/water/{houseId}",method= RequestMethod.GET)
    public Object waterQuery(@PathVariable int houseId){
        return cashService.waterQuery(houseId);
    }

    /**
     * 电费查询
     * @param houseId
     * @return
     */
    @RequestMapping(value="/electric/{houseId}",method= RequestMethod.GET)
    public Object electricQuery(@PathVariable int houseId){
        return cashService.electricQuery(houseId);
    }

    /**
     * 充值账户
     * @param req
     * @return
     */
    @RequestMapping(value="/alipay",method= RequestMethod.GET)
    public String aliPay(HttpServletRequest req){
        String body = "包租婆账户充值";//对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body。
        String subject = "用户充值";//商品的标题/交易标题/订单标题/订单关键字等。
        String out_trade_no = getOrderId();//商户网站唯一订单号
        String total_amount = req.getParameter("amount");//订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]
        //实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gateway, AlipayConfig.app_id, AlipayConfig.private_key, "json", AlipayConfig.input_charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody(body);
        model.setSubject(subject);
        model.setOutTradeNo(out_trade_no);//更换为自己的订单编号
        model.setTimeoutExpress("30m");
        model.setTotalAmount(total_amount);//订单价格
        model.setSellerId(AlipayConfig.partner);
        model.setProductCode("QUICK_MSECURITY_PAY");
        request.setBizModel(model);
        request.setNotifyUrl(AlipayConfig.service);//回调地址不可以带参数
        String orderStr = null;
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            orderStr = response.getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return orderStr;
    }

    /**
     * 充值账户
     * @return
     */
    @RequestMapping(value="/charge",method= RequestMethod.POST)
    public String chargeMoney(HttpServletRequest request) throws Exception{
        //获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        //切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
        //boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
        boolean flag = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.input_charset, "RSA2");
        if(flag){
            //账变，修改状态 修改数据库中用户的资金
            Double amount = Double.parseDouble(params.get("amount"));
            User user = CacheUtil.getInstance().getUser(request);
            cashService.transactions(user.getId(),amount,"charge","账户充值");//存储操作日志
            return cashService.chargeMoney(user.getId(),amount);
        }else{
            respEntity.setCode(500);
            respEntity.setMsg("充值失败，由于内部异常");
            return  "fail";
        }
    }

    /**
     * 提现
     * @param req
     * @return
     */
    @RequestMapping(value="/draw",method= RequestMethod.GET)
    public String drawMoney(HttpServletRequest req){
        try {
            String out_trade_no = getOrderId();
            String total_amount = req.getParameter("amount");
            AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gateway,AlipayConfig.app_id, AlipayConfig.private_key, "json", AlipayConfig.input_charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
            AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
            Map<String,String> model = new HashMap<>();
            //转账参数拼装
            model.put("out_biz_no",out_trade_no);
            model.put("payee_type","ALIPAY_LOGONID");
            User user = CacheUtil.getInstance().getUser(req);
            model.put("payee_real_name",user.getName());
            model.put("payee_account",user.getPhone());
            model.put("payer_show_name","包租婆");
            model.put("amount",total_amount);
            model.put("remark","转账");
            request.setBizContent(model.toString());
            AlipayFundTransToaccountTransferResponse response = alipayClient.execute(request);
            if (response.isSuccess()){
                cashService.transactions(user.getId(),Double.parseDouble(total_amount),"draw","账户提现");//存储操作日志
                return cashService.drawMoney(user.getId(), Double.parseDouble(total_amount));
            }else {
                return  "fail";
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return  "fail";
        }
    }

    /**
     * 生成唯一的订单号
     * @return
     */
    public static String getOrderId() {
        String newDate= DateUtil.getOrderIdByTimeStamp();
        String result="";
        Random random=new Random();
        for(int i=0;i<3;i++){
            result+=random.nextInt(10);
        }
        return newDate+result;
    }

}
