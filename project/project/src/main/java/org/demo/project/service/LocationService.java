package org.demo.project.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

@Service
public class LocationService {

    private final RestTemplate restTemplate;

    public LocationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Lấy danh sách tỉnh/thành
    public List<Map<String, Object>> getProvinces() {
        String url = "https://provinces.open-api.vn/api/p/";
        ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);
        List<Map<String, Object>> provinces = response.getBody();
        return provinces;
    }

    // Lấy danh sách quận/huyện theo mã tỉnh
    public List<Map<String, Object>> getDistricts(String provinceCode) {
        String url = "https://provinces.open-api.vn/api/p/" + provinceCode + "?depth=2";
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        Map<String, Object> data = response.getBody();
        return (List<Map<String, Object>>) data.get("districts");
    }

    // Lấy danh sách xã/phường theo mã huyện
    public List<Map<String, Object>> getWards(String districtCode) {
        String url = "https://provinces.open-api.vn/api/d/" + districtCode + "?depth=2";
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        Map<String, Object> data = response.getBody();
        return (List<Map<String, Object>>) data.get("wards");
    }

    // Lấy tên tỉnh từ mã tỉnh
    public String getProvinceName(String provinceCode) {
        String url = "https://provinces.open-api.vn/api/p/" + provinceCode;
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        Map<String, Object> province = response.getBody();
        return (String) province.get("name");
    }

    // Lấy tên quận/huyện từ mã quận/huyện
    public String getDistrictName(String districtCode) {
        String url = "https://provinces.open-api.vn/api/d/" + districtCode;
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        Map<String, Object> district = response.getBody();
        return (String) district.get("name");
    }

    // Lấy tên xã/phường từ mã xã/phường
    public String getWardName(String wardCode) {
        String url = "https://provinces.open-api.vn/api/w/" + wardCode;
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        Map<String, Object> ward = response.getBody();
        return (String) ward.get("name");
    }
}
