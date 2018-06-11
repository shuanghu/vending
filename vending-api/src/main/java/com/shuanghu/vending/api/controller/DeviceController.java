package com.shuanghu.vending.api.controller;

import com.shuanghu.vending.api.dto.output.DeviceData;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeviceController {
  @GetMapping("/devices")
  public List<DeviceData> findList(){
    List<DeviceData> devices = new ArrayList<>();
    DeviceData da = new DeviceData();
    da.setId("1");
    da.setName("n1");
    da.setPosition("sd");
    devices.add(da);
    return devices;
  }
}
