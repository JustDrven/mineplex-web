package com.mineplex.service.staff.dto;

import java.util.List;

public record StaffDTO(String name,
                       List<StaffUser> users) {
}
