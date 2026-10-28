module jmp.cloud.service.impl {
    requires transitive jmp.service.api;
    requires jmp.dto;
    exports com.epam.jmp.cloud.service.impl;
    provides com.epam.jmp.service.Service with com.epam.jmp.cloud.service.impl.CloudServiceImpl;
}
