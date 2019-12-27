package com.diamond.system.vo;

import com.diamond.system.entity.User;
import lombok.Data;

@Data
public class UserVO {
    /**
     * 更新的用户对象
     */
    private User user = new User();
    /**
     * 旧密码
     */
    private String pwdOld;
    /**
     * 新密码
     */
    private String pwdNew;

}
