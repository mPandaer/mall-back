package com.pandaer.project.server.modules.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pandaer.project.server.modules.user.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pandaer.project.server.modules.user.po.*;
import com.pandaer.project.server.modules.user.vo.LoginUserVO;
import com.pandaer.project.server.modules.user.vo.UserVO;

import java.util.List;

/**
 * 用户管理模块
 */
public interface UserService extends IService<User> {

    /**
     * 添加用户信息
     *
     * @param po 添加用户信息参数实体
     */
    void addUser(AddUserPO po);

    /**
     * 修改用户信息
     *
     * @param po 修改用户信息参数实体
     */
    void updateUser(UpdateUserPO po);


    /**
     * 批量删除用户
     *
     * @param idList 用户ID列表
     */
    void deleteUser(List<Long> idList);


    /**
     * 分页查询用户
     *
     * @param po
     */
    Page<UserVO> pageUser(PageQueryUserPO po);

    /**
     * 根据id查询用户
     *
     * @param id
     * @return
     */
    UserVO getUserBy(Long id);

    /**
     * 根据名字模糊搜索
     *
     * @param po 搜索参数实体
     * @return
     */
    Page<UserVO> pageSearchUser(PageSearchUserPO po);


    /**
     * 重置密码
     *
     * @param userId 重置密码的用户ID
     */
    void resetPassword(Long userId);


    /**
     * 更新用户头像地址
     *
     * @param userId
     * @param avatarUrl
     */
    void updateUserAvatar(Long userId, String avatarUrl);

    /**
     * 更新用户头像地址
     * @param po
     * @return
     */
    String uploadAvatar(UploadUserAvatarPO po);


    LoginUserVO userLogin(LoginUserPO po);

    void userRegister(RegisterUserPO po);


    UserVO getCurrentLoginUser(Long userId);

    void updatePassword(UpdatePasswordPO po);


    boolean isAdmin(String username);
}
