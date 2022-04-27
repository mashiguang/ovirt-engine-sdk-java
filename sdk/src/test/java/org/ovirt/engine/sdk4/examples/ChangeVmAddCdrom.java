/*
Copyright (c) 2016 Red Hat, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package org.ovirt.engine.sdk4.examples;

import org.ovirt.engine.sdk4.Connection;
import org.ovirt.engine.sdk4.services.VmCdromService;
import org.ovirt.engine.sdk4.services.VmCdromsService;
import org.ovirt.engine.sdk4.services.VmService;
import org.ovirt.engine.sdk4.services.VmsService;
import org.ovirt.engine.sdk4.types.Cdrom;
import org.ovirt.engine.sdk4.types.Vm;

import java.util.List;

import static org.ovirt.engine.sdk4.ConnectionBuilder.connection;
import static org.ovirt.engine.sdk4.builders.Builders.cdrom;
import static org.ovirt.engine.sdk4.builders.Builders.file;

// This example will connect to the server and change the CDROM attached to a virtual machine:
public class ChangeVmAddCdrom {
    public static void main(String[] args) throws Exception {
        // Create the connection to the server:
        Connection connection = connection()
                .url("https://ovirt-engine2.local/ovirt-engine/api")
                .user("admin@internal")
                .password("Admin123")
                .insecure(true)
                .build();

        // Locate the service that manages the virtual machines:
        VmsService vmsService = connection.systemService().vmsService();

        // Find the virtual machine:
        Vm vm = vmsService.list()
            .search("name=myvm-vnic")
            .send()
            .vms()
            .get(0);

        // Locate the service that manages the virtual machine:
        VmService vmService = vmsService.vmService(vm.id());

        // Locate the service that manages the CDROM devices of the virtual machine:
        VmCdromsService cdromsService = vmService.cdromsService();

        cdromsService.add().cdrom(
                cdrom()
                .file(file().id("CentOS-Stream-8-x86_64-20220414-dvd1.iso")
                ))
                .send();


        // Get the first CDROM:
        List<Cdrom> cdroms = cdromsService.list()
                .send()
                .cdroms();
        cdroms.forEach(cdrom -> {
            System.out.printf("cdrom.id: %s\n", cdrom.id());
        });

        // Close the connection to the server:
        connection.close();
    }
}
