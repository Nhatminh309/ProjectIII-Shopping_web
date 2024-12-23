package org.demo.project.controller;

import org.demo.project.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class LocationController {

    @Autowired
    private LocationService locationService;

    // API: Lấy danh sách tỉnh/thành
    @GetMapping("/api/provinces")
    public List<Map<String, Object>> getProvinces() {
        return locationService.getProvinces();
    }

    // API: Lấy danh sách quận/huyện theo mã tỉnh
    @GetMapping("/api/districts/{provinceCode}")
    public List<Map<String, Object>> getDistricts(@PathVariable String provinceCode) {
        return locationService.getDistricts(provinceCode);
    }

    // API: Lấy danh sách xã/phường theo mã huyện
    @GetMapping("/api/wards/{districtCode}")
    public List<Map<String, Object>> getWards(@PathVariable String districtCode) {
        return locationService.getWards(districtCode);
    }
}
