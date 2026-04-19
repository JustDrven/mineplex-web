package com.mineplex.service.staff.resource;

import com.mineplex.service.staff.dto.StaffDTO;
import com.mineplex.service.staff.worker.StaffWorker;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

import java.util.List;

@Path("/staff")
public class StaffResource {

    @Inject
    private StaffWorker staffWorker;

    @GET
    public List<StaffDTO> getStaffs() {
        return staffWorker.getStaffs();
    }

}
