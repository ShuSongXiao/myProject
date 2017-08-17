package com.xss.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/16 0016
 */
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String email;

    private String userName;

    private String pwd;

    private int sex;
}
