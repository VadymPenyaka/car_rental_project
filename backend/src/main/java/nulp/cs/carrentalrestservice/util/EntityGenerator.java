package nulp.cs.carrentalrestservice.util;

import nulp.cs.carrentalrestservice.entity.Admin;

public class EntityGenerator {
    public static Admin createAdmin() {
        return Admin.builder().build();
    }
}
