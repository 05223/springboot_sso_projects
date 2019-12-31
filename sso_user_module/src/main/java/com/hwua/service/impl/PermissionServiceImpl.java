package com.hwua.service.impl;

import com.hwua.domain.Permission;
import com.hwua.mapper.PermissionMapper;
import com.hwua.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<Permission> selectPermissions() throws Exception {
        List<Permission> permissions = permissionMapper.selectPermissions();
        return permissions;
    }
}
