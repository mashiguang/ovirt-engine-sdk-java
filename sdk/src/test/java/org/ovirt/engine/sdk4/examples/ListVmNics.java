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
import org.ovirt.engine.sdk4.services.*;
import org.ovirt.engine.sdk4.types.Network;
import org.ovirt.engine.sdk4.types.Nic;
import org.ovirt.engine.sdk4.types.Vm;
import org.ovirt.engine.sdk4.types.VnicProfile;

import java.util.List;

import static org.ovirt.engine.sdk4.ConnectionBuilder.connection;
import static org.ovirt.engine.sdk4.builders.Builders.*;

// This example will connect to the server and create a new virtual machine:
public class ListVmNics {
    public static void main(String[] args) throws Exception {
        // Create the connection to the server:
        Connection connection = connection()
            .url("https://ovirt-engine2.local/ovirt-engine/api")
            .user("admin@internal")
            .password("Admin123")
            .insecure(true)
            .build();

        // Get the reference to the "vms" service:
        VmsService vmsService = connection.systemService().vmsService();
        VnicProfilesService vnicProfilesService = connection.systemService().vnicProfilesService();

        // Use the "list" method of the "vms" service to list all the virtual machines of the system:
        List<Vm> vms = vmsService.list().send().vms();

        // Print the virtual machine names and identifiers:
        for (Vm vm : vms) {
            System.out.printf("####%s: %s\n", vm.name(), vm.id());

            VmNicsService vmNicsService = vmsService.vmService(vm.id()).nicsService();
            List<Nic> nics = vmNicsService.list().send().nics();

            nics.forEach(nic -> {
                System.out.printf("####nic.name: %s\n", nic.name());

                String vnicProfileId = nic.vnicProfile().id();
                System.out.printf("####vNIC配置集 vnicProfileId: %s\n", vnicProfileId);
                VnicProfile profile = vnicProfilesService.profileService(vnicProfileId).get().send().profile();
                System.out.printf("####vNIC配置集 profile.id: %s\n", profile.id());
                System.out.printf("####vNIC配置集 profile.name: %s\n", profile.name());

            });

            System.out.println();
        }

        /*
        NetworksService networksService = connection.systemService().networksService();
        List<Network> networks = networksService.list().send().networks();
        networks.forEach(network -> {
            System.out.printf("########network.name: %s, description: %s", network.name(), network.description());
        });*/

        // Close the connection to the server:
        connection.close();
    }
}
