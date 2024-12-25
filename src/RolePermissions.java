import java.util.*;

public class RolePermissions {
    private static final Map<Role, Set<Permission>> rolePermissions = new HashMap<>();
    static {
        rolePermissions.put(Role.ADMIN, EnumSet.of(Permission.CHANGE_ROLE, Permission.ADD_MEAL, Permission.EDIT_MEAL, Permission.DELETE_MEAL, Permission.MAKE_ORDER));
        rolePermissions.put(Role.EMPLOYEE, EnumSet.of(Permission.ADD_MEAL, Permission.EDIT_MEAL, Permission.DELETE_MEAL, Permission.MAKE_ORDER));
        rolePermissions.put(Role.USER, EnumSet.of(Permission.MAKE_ORDER));
    }
    public static boolean hasPermission(Role role, Permission permission) {
        return rolePermissions.getOrDefault(role, Collections.emptySet()).contains(permission);
    }
}
