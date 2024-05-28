package com.pandaer.project.server.modules.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pandaer.project.base.exception.BusinessException;
import com.pandaer.project.base.exception.util.ExceptionUtil;
import com.pandaer.project.server.common.cache.CacheService;
import com.pandaer.project.server.common.jwt.JwtProperties;
import com.pandaer.project.server.modules.user.utils.PasswordUtil;
import com.pandaer.project.server.common.jwt.JwtProvider;
import com.pandaer.project.server.common.auth.AuthService;
import com.pandaer.project.server.common.auth.entity.TokenEntity;
import com.pandaer.project.server.modules.user.config.UserProperties;
import com.pandaer.project.server.modules.user.constants.UserConstants;
import com.pandaer.project.server.modules.user.entity.User;
import com.pandaer.project.server.modules.user.po.*;
import com.pandaer.project.server.modules.user.service.UserService;
import com.pandaer.project.server.modules.user.mapper.UserMapper;
import com.pandaer.project.server.modules.user.vo.LoginUserVO;
import com.pandaer.project.server.modules.user.vo.UserVO;
import com.pandaer.project.server.util.LoginIdUtil;
import jakarta.annotation.Resource;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 用户管理模块
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserProperties properties;

    @Resource
    private JwtProperties jwtProperties;

    @Resource
    private JwtProvider jwtProvider;

    @Resource
    private AuthService authService;

    @Resource
    private CacheService cacheService;


    /**
     * 根据参数实体添加用户信息
     * 1. 校验参数合法性
     * 2. 根据用户名判断要添加的用户是否已经存在 (逻辑删除就不可恢复了)
     * 3. 添加用户信息
     * 4. 并同时为这个用户添加购物车记录
     * @param po
     * @return
     */
    @Override
    public void addUser(AddUserPO po) {
        if (ObjectUtil.isEmpty(po)) {
            throw new BusinessException(500, "参数为空");
        }
        LambdaUpdateWrapper<User> query = Wrappers.<User>lambdaUpdate().eq(User::getUsername, po.getUsername()).eq(User::getIsDelete, UserConstants.USER_NOT_DELETE);
        long count = count(query);
        if (count > 0) {
            throw new BusinessException(500, "用户已经存在");
        }

        User entity = BeanUtil.toBean(po, User.class);
        entity.setUserId(IdUtil.getSnowflakeNextId());
        entity.setCreateUser(0L);
        entity.setUpdateUser(0L);
        entity.setPassword(PasswordUtil.hashPassword(entity.getPassword()));
        if (!save(entity)) {
            throw new BusinessException(500, "添加失败");
        }
        //todo 为这个用户添加购物车记录
    }

    /**
     * 1.将PO对象转换成Entity对象
     * 2.根据用户ID来更新用户信息
     *
     * @param po 修改用户信息参数实体
     */
    @Override
    public void updateUser(UpdateUserPO po) {
        User entity = BeanUtil.toBean(po, User.class, CopyOptions.create().ignoreNullValue());
        if (!updateById(entity)) {
            throw new BusinessException(500, "更新用户信息失败");
        }
    }


    /**
     * !! 逻辑删除
     * 1. 根据ID删除用户信息
     *
     * @param idList 用户ID列表
     */
    @Override
    public void deleteUser(List<Long> idList) {
        if (CollUtil.isEmpty(idList)) {
            throw new BusinessException(500, "用户ID列表为空");
        }
        LambdaUpdateWrapper<User> update = Wrappers.lambdaUpdate();
        update.set(User::getIsDelete, UserConstants.USER_DELETE).in(User::getUserId, idList);
        if (!update(update)) {
            throw new BusinessException(500, "删除用户失败");
        }
    }

    /**
     * 1. 校验参数是否合理
     * 2. 利用MyBatis-Plus提供的page插件进行分页查询
     *
     * @param po
     * @return
     */
    @Override
    public Page<UserVO> pageUser(PageQueryUserPO po) {
        if (po.getPageSize() <= 0 || po.getCurrentPage() <= 0) {
            throw new BusinessException(500, "分页查询参数无效");
        }
        LambdaQueryWrapper<User> query = Wrappers.<User>lambdaQuery().eq(User::getIsDelete, UserConstants.USER_NOT_DELETE);
        return queryPageBy(query, po.getCurrentPage(), po.getPageSize());
    }


    /**
     * 1. 校验用户ID合法性
     * 2. 查询用户
     * 3. 调整返回对象
     *
     * @param id
     * @return
     */
    @Override
    public UserVO getUserBy(Long id) {
        if (ObjectUtil.isNull(id) || id <= 0) {
            throw new BusinessException(500, "用户ID不合法");
        }
        User entity = getById(id);
        return BeanUtil.toBean(entity, UserVO.class);
    }

    /**
     * 1. 校验po的合法性
     * 2. 分页查询对象
     * 3. 调整返回对象
     *
     * @param po 分页搜索参数实体
     * @return
     */
    @Override
    public Page<UserVO> pageSearchUser(PageSearchUserPO po) {
        if (po.getPageSize() <= 0 || po.getCurrentPage() <= 0) {
            throw new BusinessException(500, "分页查询参数无效");
        }
        LambdaQueryWrapper<User> query = Wrappers.<User>lambdaQuery();
        //拼接查询条件
        if (StrUtil.isNotEmpty(po.getName())) {
            query.likeRight(User::getUsername, po.getName());
        }

        if (StrUtil.isNotEmpty(po.getEmail())) {
            query.likeRight(User::getEmail, po.getEmail());
        }

        if (ObjectUtil.isNotNull(po.getRole())) {
            query.eq(User::getRole, po.getRole());
        }

        if (ObjectUtil.isNotNull(po.getIsEnable())) {
            query.eq(User::getIsEnable, po.getIsEnable());
        }

        return queryPageBy(query, po.getCurrentPage(), po.getPageSize());
    }


    /**
     * 重置密码
     * 1. 检查ID的合法性
     * 2. 判断用户是否存在
     * 3. 重置用户的密码为 12345678
     *
     * @param userId 重置密码的用户ID
     */
    @Override
    public void resetPassword(Long userId) {
        if (userId <= 0) {
            throw new BusinessException(500, "用户ID非法");
        }
        User entity = getById(userId);
        if (ObjectUtil.isNull(entity)) {
            throw new BusinessException(500, "用户不存在");
        }
        entity.setPassword(PasswordUtil.hashPassword(UserConstants.DEFAULT_PASSWORD));
        if (!updateById(entity)) {
            throw new BusinessException(500, "重置密码失败");
        }
    }

    /**
     * 更新用户头像地址
     * todo test 阶段
     *
     * @param userId
     * @param avatarUrl
     */
    @Override
    public void updateUserAvatar(Long userId, String avatarUrl) {
        LambdaUpdateWrapper<User> update = Wrappers.lambdaUpdate();
        update.set(User::getAvatarUrl, avatarUrl).eq(User::getUserId, userId);
        if (!update(update)) {
            throw new BusinessException(500, "更新头像失败");
        }
    }


    @Override
    public String uploadAvatar(UploadUserAvatarPO po) {
        MultipartFile file = po.getFile();
        if (file.isEmpty()) {
            throw new BusinessException(500, "头像数据不能为空");
        }
        // 生成唯一文件名
        String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();
        if (StrUtil.isEmpty(properties.getUploadDir())) {
            throw new BusinessException(500, "未设置文件上传路径");
        }
        Path filePath = Paths.get(properties.getUploadDir(), fileName);
        // 保存文件到指定目录
        try {
            Files.copy(file.getInputStream(), filePath);
        } catch (IOException e) {
            throw new BusinessException(500, "文件保存失败");
        }
        // 生成文件访问 URL
        String avatarUrl = properties.getUrlPrefix() + fileName;
        updateUserAvatar(po.getUserId(), avatarUrl);
        return avatarUrl;
    }

    /**
     * 用户登录
     * 1. 根据用户名校验用户是否存在
     * 2. 检查该用户密码的hash值是否和数据库中的一直
     * 3. 检查通过后,生成双Token + UserVO信息
     * @param po
     * @return
     */
    @Override
    public LoginUserVO userLogin(LoginUserPO po) {
        if (ObjectUtil.isNull(po)) {
            throw new BusinessException(500, "登录参数为空");
        }
        String username = po.getUsername();
        User dbUser = getUserByName(username);
        if (ObjectUtil.isNull(dbUser)) {
            throw new BusinessException(500, "当前登录用户不存在");
        }
        if (!PasswordUtil.verifyPassword(dbUser.getPassword(), po.getPassword())) {
            throw new BusinessException(500, "密码不正确");
        }

        //生成双Token
        Map<String,Object> map = MapUtil.<String,Object>builder().put("userId",dbUser.getUserId()).build();
        TokenEntity tokenEntity = authService.genToken(map);
        //保存到Redis中
        cacheService.saveRefreshToken(String.valueOf(dbUser.getUserId()),
                tokenEntity.getRefreshToken(),jwtProperties.getRefreshExpiration());

        //设置返回对象
        LoginUserVO loginUserVO = new LoginUserVO();
        loginUserVO.setAccessToken(tokenEntity.getAccessToken());
        loginUserVO.setRefreshToken(tokenEntity.getRefreshToken());
        UserVO vo = BeanUtil.toBean(dbUser, UserVO.class);
        loginUserVO.setUser(vo);
        return loginUserVO;
    }

    /**
     * 用户注册
     * 1. 通过用户名检查用户是否已经注册了
     * 2. 没有注册就添加用户
     * @param po 用户注册参数实体
     */
    @Override
    public void userRegister(RegisterUserPO po) {
        if (ObjectUtil.isNull(po)) {
            throw new BusinessException(500, "注册参数为空");
        }
        User entity = getUserByName(po.getUsername());
        if (ObjectUtil.isNotNull(entity)) {
            throw new BusinessException(500, "该用户名已经被注册,请重试");
        }
        AddUserPO addUserPO = BeanUtil.toBean(po, AddUserPO.class);
        addUser(addUserPO);
    }

    /**
     * 获取当前用户登录信息
     * @param userId
     * @return
     */
    @Override
    public UserVO getCurrentLoginUser(Long userId) {
        User entity = getById(userId);
        if (ObjectUtil.isNull(entity)) {
            throw new BusinessException(500,"登录用户不存在");
        }
        return BeanUtil.toBean(entity,UserVO.class);
    }


    /**
     * 更新密码
     * 1. 根据当前登录用户获取密码
     * 2. 验证旧密码
     * 3. 验证通过,更新密码
     * @param po
     */
    @Override
    public void updatePassword(UpdatePasswordPO po) {
        if (ObjectUtil.isNull(po)) {
            ExceptionUtil.business(500,"参数异常");
        }
        if (ObjectUtil.notEqual(po.getConfirmPassword(),po.getNewPassword())) {
            ExceptionUtil.business(500,"两次输入的密码不一致");
        }
        Long userId = LoginIdUtil.getUserId();
        User entity = getById(userId);
        if (!PasswordUtil.verifyPassword(entity.getPassword(),po.getOldPassword())) {
            ExceptionUtil.business(500,"密码验证失败");
        }
        entity.setPassword(PasswordUtil.hashPassword(po.getNewPassword()));
        if (!updateById(entity)) {
            ExceptionUtil.business(500,"更新密码失败");
        }
    }

    @Override
    public boolean isAdmin(String username) {
        User entity = getUserByName(username);
        if (ObjectUtil.isNull(entity)) {
            ExceptionUtil.business(500,"当前用户不存在");
        }
        return entity.getRole() == 1;
    }


    /**
     * 通过用户名查询用户
     *
     * @param username
     * @return
     */
    private User getUserByName(String username) {
        LambdaQueryWrapper<User> query = Wrappers.lambdaQuery();
        query.eq(User::getUsername, username).eq(User::getIsDelete, UserConstants.USER_NOT_DELETE).eq(User::getIsEnable, UserConstants.USER_ENABLE);
        return getOne(query);
    }


    private Page<UserVO> queryPageBy(LambdaQueryWrapper<User> query, long currentPage, long pageSize) {
        Page<User> userPage = new Page<>();
        userPage.setCurrent(currentPage);
        userPage.setSize(pageSize);
        Page<User> resPage = page(userPage, query);
        //调整返回对象
        Page<UserVO> userVOPage = new Page<>();
        BeanUtil.copyProperties(resPage, userVOPage, "records");
        List<UserVO> list = resPage.getRecords().stream().map((user) -> BeanUtil.toBean(user, UserVO.class)).toList();
        userVOPage.setRecords(list);
        return userVOPage;
    }
}




