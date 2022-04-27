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
import org.ovirt.engine.sdk4.builders.Builders;
import org.ovirt.engine.sdk4.services.*;
import org.ovirt.engine.sdk4.types.*;

import java.util.List;

import static org.ovirt.engine.sdk4.ConnectionBuilder.connection;
import static org.ovirt.engine.sdk4.builders.Builders.*;

// This example will connect to the server and change the CDROM attached to a virtual machine:
public class ChangeVmCdBeforePowerOn {
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

        /* 仅列出ovirt中预制的操作系统，用于下拉框显示，包括：winxp、win7、redhat、debian等等
        OperatingSystemsService operatingSystemsService = connection.systemService().operatingSystemsService();
        List<OperatingSystemInfo> operatingSystemInfos = operatingSystemsService.list().send().operatingSystem();
        operatingSystemInfos.forEach(operatingSystemInfo -> {
            System.out.printf("os.name: %s\n", operatingSystemInfo.name());
        });*/

        //vm对象是这一个现有的虚拟机
        vmService.update().vm(vm().os(operatingSystem().boot(boot().devices(BootDevice.HD, BootDevice.CDROM)))).send();

        // Get the first CDROM:
        Cdrom cdrom = cdromsService.list()
            .send()
            .cdroms()
            .get(0);

        // Locate the service that manages the CDROM device found in the previous step:
        VmCdromService cdromService = cdromsService.cdromService(cdrom.id());

        // Change the CDROM disk of the virtual machine to 'my_iso_file.iso'. By default the below operation changes
        // permanently the disk that will be visible to the virtual machine after the next boot, but it doesn't have
        // any effect on the currently running virtual machine. If you want to change the disk that is visible to the
        // current running virtual machine, change the value of the 'current' parameter to 'true'.
        cdromService.update()
            .cdrom(
                cdrom()
                .file(
                    file()
                    .id("CentOS-Stream-8-x86_64-20220414-dvd1.iso")
                )
            )
            .current(false)
            .send();

        //这里仅加载了iso文件，并没有设置以这个cdrom引导进系统
        //cdromsService.add().cdrom(cdrom).send();

        // Close the connection to the server:
        connection.close();
    }
}
