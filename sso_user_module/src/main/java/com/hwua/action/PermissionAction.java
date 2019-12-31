package com.hwua.action;

import com.alibaba.fastjson.JSON;
import com.hwua.domain.Permission;
import com.hwua.service.PermissionService;
import com.hwua.util.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PermissionAction {

    @Autowired
    private PermissionService permissionService;

    @PostMapping("user/getPermissions")
    public ResponseData getPermissions() throws Exception{
        ResponseData responseData = new ResponseData();
        responseData.setCode(0);
        List<Permission> permissions = permissionService.selectPermissions();
        String s = JSON.toJSONString(permissions);
        responseData.setMessage(s);
        return responseData;
    }
}
