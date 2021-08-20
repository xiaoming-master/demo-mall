package com.ming.mall.service.impl;

import com.ming.mall.model.UmsAdminPermissionRelation;
import com.ming.mall.mapper.UmsAdminPermissionRelationMapper;
import com.ming.mall.service.IUmsAdminPermissionRelationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 后台用户和权限关系表(除角色中定义的权限以外的加减权限) 服务实现类
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
@Service
public class UmsAdminPermissionRelationServiceImpl extends ServiceImpl<UmsAdminPermissionRelationMapper, UmsAdminPermissionRelation> implements IUmsAdminPermissionRelationService {

}
