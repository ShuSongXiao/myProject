package com.xss.spider.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by xiaoss390 on 2017/11/7
 * 幸运28
 */
@Setter
@Getter
@NoArgsConstructor
@Accessors(chain = true)
public class LuckyInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String issueNo;

    private String lotteryTime;

    private String formula;

    private String result;

    private Date createTime;

}
