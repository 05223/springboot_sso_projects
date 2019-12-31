package com.hwua.service;

import com.hwua.domain.Permission;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PermissionService {

    List<Permission> selectPermissions() throws Exception;
}
