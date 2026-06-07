package com.Kalabekov.Computersservice.listener;

import com.Kalabekov.Computersservice.event.LicenseEvent;
import com.Kalabekov.Computersservice.model.Software;
import com.Kalabekov.Computersservice.service.SoftwareService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LicenseEventListener {

    @Autowired
    private SoftwareService softwareService;

    @RabbitListener(queues = "license.queue")
    public void handleLicenseEvent(LicenseEvent event) {
        System.out.println("Received event: " + event.getEventType() + " for " + event.getSoftwareName());

        switch (event.getEventType()) {
            case "CREATE":
            case "UPDATE":
                applyLicenseToSoftware(event.getSoftwareName(), true);
                break;
            case "DELETE":
                applyLicenseToSoftware(event.getSoftwareName(), false);
                break;
            default:
                System.out.println("Unknown event type: " + event.getEventType());
        }
    }

    private void applyLicenseToSoftware(String softwareName, boolean licensed) {
        boolean found = false;

        for (Software software : softwareService.getAllSoftware()) {
            if (software.getSoftwareName().equalsIgnoreCase(softwareName)) {
                software.setLicensed(licensed);
                softwareService.updateSoftware(software.getId(), software);
                found = true;
                System.out.println("Updated software: " + softwareName + " -> isLicensed=" + licensed);
            }
        }

        if (!found && licensed) {
            Software newSoftware = new Software();
            newSoftware.setSoftwareName(softwareName);
            newSoftware.setLicensed(true);
            softwareService.createSoftware(newSoftware);
            System.out.println("Created new software: " + softwareName + " with license");
        }
    }
}