package com.xss.spider.service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.xss.spider.Entity.GoodsInfo;
import com.xss.spider.Entity.LuckyInfo;
import com.xss.spider.common.HttpClientUtils;
import com.xss.spider.dao.LuckyInfoDao;
import com.xss.util.LogUtil;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by xiaoss390 on 2017/11/8.
 */
@Service
public class LuckyService {

    @Autowired
    private LuckyInfoDao luckyInfoDao;

    private static String HTTPS_PROTOCOL = "https:";

    /**
     * 保存爬去的数据
     * @param url  爬取得地址
     * @param params 参数
     */
    public void spiderData(String url, Map<String, String> params) {
        String html = HttpClientUtils.sendGet(url, null, params);
        if(!StringUtils.isBlank(html)) {
            List<LuckyInfo> luckyInfos = parseHtml(html);
            luckyInfoDao.deleteBatch();
            luckyInfoDao.saveBatch(luckyInfos);
        }
    }

    /**
     * 解析html
     * @param html
     */
    private List<LuckyInfo> parseHtml(String html) {
        //商品集合
        List<LuckyInfo> luckyInfos = Lists.newArrayList();
        /**
         * 获取dom并解析
         */
        Document document = Jsoup.parse(html);
        Elements elements = document.
                select("div[class=containers content_index clear]")
                .select("div[class=lucky]")
                .select("table[class=list]").select("tbody").select("tr");
        int index = 0;
        for(Element element : elements) {
            if(index == 0){
                index++;
                continue;
            }
            String issueNo = element.child(0).html();
            String lotteryTime = element.child(1).text();
            String foumula = element.child(2).text().split("=")[0];
            String result = element.child(2).select("span[class=ball_lucky]").text();
            LuckyInfo luckyInfo = new LuckyInfo();
            luckyInfo.setIssueNo(issueNo)
                    .setLotteryTime(lotteryTime)
                    .setFormula(foumula)
                    .setResult(result);
            luckyInfos.add(luckyInfo);
            String jsonStr = JSON.toJSONString(luckyInfos);
            LogUtil.ROOT_LOG.info("成功爬取【" + jsonStr + "】的基本信息 ");
            if(index ++ == 10) {
                break;
            }
        }
        return luckyInfos;
    }
}
