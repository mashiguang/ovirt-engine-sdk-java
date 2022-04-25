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
import org.ovirt.engine.sdk4.services.VmsService;

import static org.ovirt.engine.sdk4.ConnectionBuilder.connection;
import static org.ovirt.engine.sdk4.builders.Builders.*;

// This example will connect to the server and create a new virtual machine:
public class AddVmMemory {
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

        // Use the "add" method to create a new virtual machine:
        vmsService.add()
            .vm(
                vm()
                .name("myvm")
                .cluster(
                    cluster()
                    .name("Default")
                ).memory(1024*1024*1024*4L) //参数以Kb为单位
                .template(
                    template()
                    .name("Blank")
                )
            )
            .send();

        // Close the connection to the server:
        connection.close();
    }
}
