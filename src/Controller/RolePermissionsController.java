package Controller;

import Model.PermissionModel;
import Model.RoleModel;

import java.util.*;

public class RolePermissionsController {
    private static final Map<RoleModel, Set<PermissionModel>> rolePermissions = new HashMap<>();
    static {
        rolePermissions.put(RoleModel.ADMIN, EnumSet.of(PermissionModel.CHANGE_ROLE, PermissionModel.ADD_MEAL,
                PermissionModel.EDIT_MEAL, PermissionModel.DELETE_MEAL, PermissionModel.MAKE_ORDER));
        rolePermissions.put(RoleModel.EMPLOYEE, EnumSet.of(PermissionModel.ADD_MEAL, PermissionModel.EDIT_MEAL,
                PermissionModel.DELETE_MEAL, PermissionModel.MAKE_ORDER));
        rolePermissions.put(RoleModel.USER, EnumSet.of(PermissionModel.MAKE_ORDER));
    }

    public static boolean hasPermission(RoleModel role, PermissionModel permission) {
        return rolePermissions.getOrDefault(role, Collections.emptySet()).contains(permission);
    }
}
