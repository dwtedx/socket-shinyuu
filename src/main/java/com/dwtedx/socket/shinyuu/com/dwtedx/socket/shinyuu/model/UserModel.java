package com.dwtedx.socket.shinyuu.com.dwtedx.socket.shinyuu.model;

import com.sun.tracing.dtrace.ArgsAttributes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName UserModel
 * Description 用户
 * Create by shinyuu on 2022/4/7 16:19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {

    private Long id;
    private String name;
    private String nickName;

}
