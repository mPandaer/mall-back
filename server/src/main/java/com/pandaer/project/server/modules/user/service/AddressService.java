package com.pandaer.project.server.modules.user.service;

import com.pandaer.project.server.modules.user.entity.Address;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pandaer.project.server.modules.user.po.AddAddressPO;
import com.pandaer.project.server.modules.user.po.DeleteAddressPO;
import com.pandaer.project.server.modules.user.po.UpdateAddressPO;
import com.pandaer.project.server.modules.user.vo.AddressVO;

import java.util.List;

/**
* @author pandaer
* @description 针对表【addresses】的数据库操作Service
* @createDate 2024-05-20 09:27:41
*/
public interface AddressService extends IService<Address> {


    AddressVO getAddressById(Long addressId);

    String addAddress(AddAddressPO po);

    void updateAddress(UpdateAddressPO po);

    void deleteAddress(DeleteAddressPO po);

    List<AddressVO> queryAllAddress(String userId);

}
