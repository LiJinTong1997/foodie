package com.imooc.service;

import com.imooc.pojo.Stu;
import com.imooc.pojo.Users;
import com.imooc.pojo.bo.UserBO;

public interface UserService {

    /**
     * 查询用户是否存在
     * @param username 用户名
     */
    public boolean queryUsernameIsExist(String username);

    /**
     * 创建用户
     * @param userBO 用户BO类
     * @return
     */
    public Users createUser(UserBO userBO);

    /**
     * 检索用户名和密码是否匹配  用于登录
     * @param username 用户名
     * @param password 密码
     * @return
     */
    public Users queryUserForLogin(String username,String password);
}
