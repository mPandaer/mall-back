package com.pandaer.project.server.modules.user.service.impl;

import cn.hutool.json.JSONUtil;
import com.pandaer.project.server.modules.user.po.AddAddressPO;
import com.pandaer.project.server.modules.user.po.DeleteAddressPO;
import com.pandaer.project.server.modules.user.po.UpdateAddressPO;
import com.pandaer.project.server.modules.user.service.AddressService;
import com.pandaer.project.server.modules.user.vo.AddressVO;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AddressServiceTest {

    @Resource
    private AddressService addressService;

    @Test
    void getAddressById() {

    }

    @Test
    void addAddress() {
        AddAddressPO po = new AddAddressPO();
        po.setUserId(123L);
        po.setRecipientName("李文豪");
        po.setRecipientPhone("13220217968");
        po.setProvince("重庆");
        po.setCity("重庆");
        po.setAddress("市区");
        addressService.addAddress(po);

    }

    @Test
    void updateAddress() {
        UpdateAddressPO po = new UpdateAddressPO();
        po.setAddressId(1794898178626199552L);
        po.setUserId(123L);
        po.setRecipientName("李文豪23");
        addressService.updateAddress(po);

    }

    @Test
    void deleteAddress() {
        DeleteAddressPO deleteAddressPO = new DeleteAddressPO();
        deleteAddressPO.setAddressId(1794898178626199552L);
        deleteAddressPO.setUserId(123L);
        addressService.deleteAddress(deleteAddressPO);
    }

    @Test
    void queryAllAddress() {

        List<AddressVO> list = addressService.queryAllAddress("123");
        System.out.println(JSONUtil.toJsonPrettyStr(list));
    }
}