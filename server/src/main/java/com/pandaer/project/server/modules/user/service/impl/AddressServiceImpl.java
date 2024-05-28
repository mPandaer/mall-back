package com.pandaer.project.server.modules.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pandaer.project.base.exception.util.ExceptionUtil;
import com.pandaer.project.base.utils.IdUtil;
import com.pandaer.project.server.modules.user.constants.UserConstants;
import com.pandaer.project.server.modules.user.entity.Address;
import com.pandaer.project.server.modules.user.po.AddAddressPO;
import com.pandaer.project.server.modules.user.po.DeleteAddressPO;
import com.pandaer.project.server.modules.user.po.UpdateAddressPO;
import com.pandaer.project.server.modules.user.service.AddressService;
import com.pandaer.project.server.modules.user.mapper.AddressMapper;
import com.pandaer.project.server.modules.user.vo.AddressVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author pandaer
* @description 针对表【addresses】的数据库操作Service实现
* @createDate 2024-05-20 09:27:41
*/
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address>
    implements AddressService{

    @Override
    public AddressVO getAddressById(Long addressId) {
        LambdaQueryWrapper<Address> query = Wrappers.lambdaQuery();
        query.eq(Address::getAddressId,addressId).eq(Address::getIsDelete,UserConstants.USER_NOT_DELETE);
        Address entity = getOne(query);
        return BeanUtil.toBean(entity, AddressVO.class);
    }

    /**
     * 增加地址信息
     * @param po
     */
    @Override
    public String addAddress(AddAddressPO po) {
        Address entity = BeanUtil.toBean(po, Address.class);
        Long addressId = IdUtil.genNextId();
        entity.setAddressId(addressId);
        if (!save(entity)) {
            ExceptionUtil.business(500,"添加失败");
        }

        return String.valueOf(addressId);

    }

    /**
     * 判断是否存在该地址
     * 填充新数据
     * 更新
     * @param po
     */
    @Override
    public void updateAddress(UpdateAddressPO po) {
        if (ObjectUtil.isNull(po)) {
            ExceptionUtil.business(500,"参数异常");
        }
        Address entity = getById(po.getAddressId());
        if (ObjectUtil.isNull(entity)) {
            ExceptionUtil.business(500,"修改的地址不存在");
        }
        BeanUtil.copyProperties(po,entity);
        if (!updateById(entity)) {
            ExceptionUtil.business(500,"更新地址失败");
        }
    }

    @Override
    public void deleteAddress(DeleteAddressPO po) {
        if (ObjectUtil.isNull(po)) {
            ExceptionUtil.business(500,"参数异常");
        }
        LambdaQueryWrapper<Address> query = Wrappers.lambdaQuery();
        query.eq(Address::getAddressId,po.getAddressId()).eq(Address::getUserId,po.getUserId());
        Address entity = getOne(query);
        if (ObjectUtil.isNull(entity)) {
            ExceptionUtil.business(500,"地址不存在");
        }
        entity.setIsDelete(UserConstants.USER_DELETE);
        if (!updateById(entity)) {
            ExceptionUtil.business(500,"删除失败");
        }
    }

    @Override
    public List<AddressVO> queryAllAddress(String userId) {
        LambdaQueryWrapper<Address> query = Wrappers.lambdaQuery();
        query.eq(Address::getUserId,Long.valueOf(userId)).eq(Address::getIsDelete,UserConstants.USER_NOT_DELETE);
        List<Address> list = list(query);
        return list.stream().map(item -> BeanUtil.toBean(item,AddressVO.class)).toList();
    }
}




