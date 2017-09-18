package com.xss.model;

import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/** 商品信息表 --> GOODS_INFO */
@Setter
@Getter
@NoArgsConstructor
@Accessors(chain = true)
public class GoodsInfo implements Serializable {
    private Integer id;

    /** 商品ID --> GOODS_ID */
    private String goodsId;

    /** 商品名称 --> GOODS_NAME */
    private String goodsName;

    /** 商品图片地址 --> IMG_URL */
    private String imgUrl;

    /** 商品标价 --> GOODS_PRICE */
    private String goodsPrice;

    private static final long serialVersionUID = 1L;
}