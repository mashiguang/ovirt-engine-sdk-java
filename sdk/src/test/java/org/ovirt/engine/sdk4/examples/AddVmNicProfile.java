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
import org.ovirt.engine.sdk4.builders.VmBuilder;
import org.ovirt.engine.sdk4.services.VmNicsService;
import org.ovirt.engine.sdk4.services.VmsService;
import org.ovirt.engine.sdk4.services.VnicProfilesService;
import org.ovirt.engine.sdk4.types.Vm;
import org.ovirt.engine.sdk4.types.VnicProfile;

import static org.ovirt.engine.sdk4.ConnectionBuilder.connection;
import static org.ovirt.engine.sdk4.builders.Builders.*;

// This example will connect to the server and create a new virtual machine:
public class AddVmNicProfile {
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

        // Use the "add" method to create a new virtual machine:
        String vnicProfileId = "0000000a-000a-000a-000a-000000000398";

        VnicProfile vnicProfile = vnicProfile().id(vnicProfileId).build();

        VmBuilder vmBuilder = vm()
                .name("myvm-vnic")
                .cluster(
                        cluster()
                                .name("Default")
                ).cpu(cpu().topology(cpuTopology().cores(3))) //参数3是虚拟cpu的总数
                .memory(1024 * 1024 * 1024 * 4L) //参数以Kb为单位
                .template(
                        template()
                                .name("Blank")
                );
        Vm vm = vmBuilder.build();
        System.out.printf("####此时vm还没有id，vm.id: %s\n", vm.id());

        Vm vm1 = vmsService.add()
                .vm(vm)
                .send().vm();

        System.out.printf("####这时vm才有了id，vm1.id: %s\n", vm1.id());

        //从实验看，vm创建完成后，才能为其配置网络
        VmNicsService vmNicsService = vmsService.vmService(vm1.id()).nicsService();
        vmNicsService.add().nic(nic().name("nic1").vnicProfile(vnicProfile)).send();

        // Close the connection to the server:
        connection.close();
    }
}
