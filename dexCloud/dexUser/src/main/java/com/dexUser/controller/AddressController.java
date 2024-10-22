package com.dexUser.controller;

import com.dexUser.service.AddressService;
import domain.addressPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utils.JsonObject;

@RestController
@CrossOrigin
@RequestMapping("/address")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @RequestMapping("/addUserAddress")
    JsonObject addUserAddress(addressPojo address){
        System.out.println("address-Controller"+address.toString());
        return addressService.addUserAddress(address);
    }

    @RequestMapping("/getUserAddress")
    JsonObject getUserAddress(int user_id){
        return addressService.getUserAddress(user_id);
    }

    @RequestMapping("/delUserAddress")
    JsonObject delUserAddress(int address_id){
        return addressService.delUserAddress(address_id);
    }

    @RequestMapping("/modifyAddress")
    JsonObject modifyAddress(addressPojo address){
        return addressService.modifyAddress(address);
    }

}
