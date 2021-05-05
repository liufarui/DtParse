package com.github.liufarui.dtParse.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.liufarui.dtParse.dao.SqlSelect;
import com.github.liufarui.dtParse.model.DtSource;
import com.github.liufarui.dtParse.model.DtValue;
import com.github.liufarui.dtParse.util.StringUtils;
import org.junit.Test;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * @Package: com.github.liufarui.dtParse.service
 * @ClassName: DtParseService
 * @Description:
 * @Author: liufarui
 * @CreateDate: 2021/5/4 11:19 下午
 * @Copyright: Copyright (c)2021 JDL.CN All Right Reserved
 * @Since: JDK 1.8
 * @Version: V1.0
 */
public class DtParseService {

    private String testStr() {
        return "{\n" +
                "    \"businessIdentity\": {\n" +
                "        \"businessScene\": \"receive\",\n" +
                "        \"businessStrategy\": \"StandardCargoExpress\",\n" +
                "        \"businessType\": \"reverse_order\",\n" +
                "        \"businessUnit\": \"cn_jdl_c2c\",\n" +
                "        \"fulfillmentUnit\": \"JDL.ORDER.C2C\"\n" +
                "    },\n" +
                "    \"cargoInfos\": [\n" +
                "        {\n" +
                "            \"cargoName\": \"测试商品\",\n" +
                "            \"cargoQuantity\": {\"value\":1},\n" +
                "            \"cargoType\": \"2\",\n" +
                "            \"cargoVolume\": {\n" +
                "                \"unit\": \"CM3\",\n" +
                "                \"value\": 301\n" +
                "            },\n" +
                "            \"cargoWeight\": {\n" +
                "                \"unit\": \"KG\",\n" +
                "                \"value\": 10\n" +
                "            }\n" +
                "        }\n" +
                "    ],\n" +
                "    \"channelInfo\": {\n" +
                "        \"channelCustomerNo\": \"EBU4398046516167\",\n" +
                "        \"channelNo\": \"0010012\",\n" +
                "        \"channelOperateTime\": 1618808804053,\n" +
                "        \"channelOrderNo\": \"1614033334444\",\n" +
                "        \"customerOrderNo\": \"c2c12356888d1\",\n" +
                "        \"systemCaller\": \"JD-H5\",\n" +
                "        \"systemSubCaller\": \"xcx\"\n" +
                "    },\n" +
                "    \"consigneeInfo\": {\n" +
                "        \"addressInfo\": {\n" +
                "            \"address\": \"北京物资学院院内东区2号学生公寓楼门口\",\n" +
                "            \"cityName\": \"通州区\",\n" +
                "            \"cityNo\": \"2809\",\n" +
                "            \"countyName\": \"富河大街1号\",\n" +
                "            \"countyNo\": \"51081\",\n" +
                "            \"provinceName\": \"北京\",\n" +
                "            \"provinceNo\": \"1\",\n" +
                "            \"townName\": \"永顺镇\",\n" +
                "            \"townNo\": \"0\"\n" +
                "        },\n" +
                "        \"consigneeCompany\": \"盛大传媒有限公司\",\n" +
                "        \"consigneeIdNo\": \"3714811988030467541\",\n" +
                "        \"consigneeIdType\": 1,\n" +
                "        \"consigneeMobile\": \"13391751284\",\n" +
                "        \"consigneeName\": \"李四\",\n" +
                "        \"consigneePhone\": \"0755-89891811\",\n" +
                "        \"consigneeZipCode\": \"110110\"\n" +
                "    },\n" +
                "    \"consignorInfo\": {\n" +
                "        \"addressInfo\": {\n" +
                "            \"address\": \"京东总部\",\n" +
                "            \"cityName\": \"大兴区\",\n" +
                "            \"cityNo\": \"2810\",\n" +
                "            \"countyName\": \"亦庄经济开发区\",\n" +
                "            \"countyNo\": \"51081\",\n" +
                "            \"provinceName\": \"北京\",\n" +
                "            \"provinceNo\": \"1\",\n" +
                "            \"townName\": \"梨园镇\",\n" +
                "            \"townNo\": \"0\"\n" +
                "        },\n" +
                "        \"consignorCompany\": \"XX传媒有限公司\",\n" +
                "        \"consignorIdNo\": \"37148119870304213211\",\n" +
                "        \"consignorIdType\": 1,\n" +
                "        \"consignorMobile\": \"13691751284\",\n" +
                "        \"consignorName\": \"张三\",\n" +
                "        \"consignorPhone\": \"0755-79891811\",\n" +
                "        \"consignorZipCode\": \"100011\"\n" +
                "    },\n" +
                "    \"customerInfo\": {\n" +
                "        \"accountNo\": \"29K21264\"\n" +
                "    },\n" +
                "    \"financeInfo\": {\n" +
                "        \"payment\": 3,\n" +
                "        \"paymentAccountNo\": \"1235\",\n" +
                "        \"paymentStage\": 1,\n" +
                "        \"preemptType\": 0,\n" +
                "        \"settlementType\": 2\n" +
                "    },\n" +
                "    \"initiatorType\": 4,\n" +
                "    \"operator\": \"ww30\",\n" +
                "    \"orderSubType\": \"C2C\",\n" +
                "    \"orderType\": \"504\",\n" +
                "    \"productInfos\": [\n" +
                "        {\n" +
                "            \"extendProps\": {},\n" +
                "            \"productNo\": \"ed-m-0001\",\n" +
                "            \"productType\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"parentNo\": \"ed-m-0001\",\n" +
                "            \"productNo\": \"ed-a-0005\",\n" +
                "            \"productType\": 2\n" +
                "        }\n" +
                "    ],\n" +
                "    \"refOrderInfo\": {\n" +
                "        \"originalOrderNo\": \"TC0010000002312\",\n" +
                "        \"purchaseOrderNo\": \"12345678\",\n" +
                "        \"reservationOrderNo\": \"12345678\",\n" +
                "        \"waybillNo\": \"JDX000156975260\"\n" +
                "    },\n" +
                "    \"shipmentInfo\": {\n" +
                "        \"expectPickupEndTime\": 1620273600000,\n" +
                "        \"expectPickupStartTime\": 1620270000000,\n" +
                "        \"pickupType\": 1\n" +
                "    }\n" +
                "}";
    }

    public String testStr2() {
        return "{\"ext\":{},\"locale\":\"zh_CN\",\"tenantId\":\"1000\",\"timeZone\":\"GMT+8\",\"traceId\":\"WG16122478551555564\",\"channelInfo\":{\"channelOperateTime\":1619072879305},\"consigneeInfo\":{\"addressInfo\":{\"address\":\"朝阳公园\",\"cityName\":\"朝阳区\",\"cityNo\":\"72\",\"countyName\":\"三环到四环之间\",\"countyNo\":\"2819\",\"provinceName\":\"北京\",\"provinceNo\":\"1\",\"townName\":\"朝阳公园\",\"townNo\":\"0\"},\"consigneeCompany\":\"盛大传媒有限公司\",\"consigneeIdNo\":\"3714811988030467541\",\"consigneeIdType\":1,\"consigneeMobile\":\"13391751284\",\"consigneeName\":\"李四\",\"consigneePhone\":\"0755-89891811\",\"consigneeZipCode\":\"110110\"},\"consignorInfo\":{\"addressInfo\":{\"address\":\"京东总部4号楼\",\"cityName\":\"大兴区\",\"cityNo\":\"2810\",\"countyName\":\"亦庄经济开发区\",\"countyNo\":\"51081\",\"provinceName\":\"北京\",\"provinceNo\":\"1\",\"townName\":\"梨园镇\",\"townNo\":\"0\"},\"consignorMobile\":\"18655555513\",\"consignorName\":\"苑测试\"},\"initiatorType\":4,\"operator\":\"bjwangzhong1002\",\"orderNo\":\"EO0010000003780\",\"remark\":\"修改收寄件地址\"}";
    }

    @Test
    public void test1() {
        Map<String, DtValue> stringDtValueMap = transToMap(testStr());
        System.out.println(stringDtValueMap);
    }

    @Test
    public void test2() throws SQLException, ExecutionException, InterruptedException {
        SqlSelect sqlSelect = new SqlSelect("jdbc:mysql://mysql-cn-north-1-f43a6fe74d8e4ae7.rds.jdcloud.com:3358/cp_order", "adminwl", "2yfTkySnW6EcD");

        Map<String, DtValue> sqlDataMap = sqlSelect.getDataMap("t_order_cargo,t_order_attachment,t_order_customer_channel", "order_no", "EC0010000000546");
        System.out.println(sqlDataMap);
    }

    @Test
    public void test3() throws SQLException, ExecutionException, InterruptedException {
        String check = check("jdbc:mysql://mysql-cn-north-1-f43a6fe74d8e4ae7.rds.jdcloud.com:3358/cp_order",
                "adminwl",
                "2yfTkySnW6EcD",
                "t_order_cargo,t_order_attachment,t_order_customer_channel,t_order_cargo,t_order_consign,t_order_market,t_order_service_product,t_order_shipment,t_order_transaction_cost",
                "order_no",
                "EO0010000003780",
                StringUtils.humpToLine(testStr2())
        );
        System.out.println(check);
    }

    public String check(String url,
                        String user,
                        String password,
                        String tables,
                        String cColumn,
                        String cColumnValue,
                        String jsonStr) throws SQLException, ExecutionException, InterruptedException {
        SqlSelect sqlSelect = new SqlSelect(url, user, password);

        Map<String, DtValue> sqlDataMap = sqlSelect.getDataMap(tables, cColumn, cColumnValue);

        Map<String, DtValue> jsonDataMap = transToMap(jsonStr);

        StringBuilder sbNotExisted = new StringBuilder();
        StringBuilder sbFailed = new StringBuilder();

        jsonDataMap.forEach((key, value) -> {
            String dbKey = StringUtils.humpToLine(key);
            if (!sqlDataMap.containsKey(key)) {
                sbNotExisted.append(key).append(" 不存在！\n");
            } else if (!value.getValue().equals(sqlDataMap.get(dbKey).getValue())) {
                sbFailed.append(key).append(" 比对失败！Json值：").append(value.getValue()).append("，DB值：").append(sqlDataMap.get(dbKey).getValue()).append("\n");
            }
        });

        return sbNotExisted.append(sbFailed).toString();
    }

    private Map<String, DtValue> transToMap(String jsonStr) {
        jsonStr = jsonStr.replace(" ", "").replace("\n", "");

        Map<String, Object> initMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            initMap = mapper.readValue(jsonStr, Map.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Map<String, DtValue> retMap = new HashMap<>();

        retMap.putAll(transToMap(initMap, null));

        return retMap;
    }

    private Map<String, DtValue> transToMap(Object mapObject, DtValue parent) {
        Map<String, Object> initMap = (Map<String, Object>) mapObject;

        Map<String, DtValue> retMap = new HashMap<>();

        initMap.forEach((key, value) -> {
            if (value.getClass().isArray()) {
                System.out.printf("暂时不支持列表：%s", key);
            } else if (value instanceof Map) {
                // 父节点暂时不添加到map中
                DtValue thisDtValue = new DtValue(DtSource.JSON, value.toString(), parent);
                retMap.putAll(transToMap(value, thisDtValue));
            } else {
                DtValue dtValue = new DtValue(DtSource.JSON, value.toString(), parent);
                retMap.put(key, dtValue);
            }
        });

        return retMap;
    }
}
