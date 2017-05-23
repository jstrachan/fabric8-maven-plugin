/*
 * Copyright 2016 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version
 * 2.0 (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */
package io.fabric8.maven.enricher.app;

import io.fabric8.kubernetes.api.KubernetesHelper;
import io.fabric8.kubernetes.api.model.ContainerBuilder;
import io.fabric8.kubernetes.api.model.HasMetadata;
import io.fabric8.kubernetes.api.model.KubernetesList;
import io.fabric8.kubernetes.api.model.ServiceSpecBuilder;
import io.fabric8.maven.enricher.app.model.App;
import io.fabric8.maven.enricher.app.model.AppBuilder;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 */
public class AppMarshalTest {
    @Test
    public void testMarshal() throws Exception {
        App app = new AppBuilder().withName("cheese").
                withReplicas(1).
                addToLabels("app", "cheese").
                addToAnnotations("com.acme", "foo").
                withService(new ServiceSpecBuilder().addNewPort().withPort(80).withNewTargetPort().withIntVal(8080).endTargetPort().endPort().build()).
                build();

        // TODO shame we can't build containers in the above!
        app.setContainers(Arrays.asList(new ContainerBuilder().
                withImage("foo/bar:123").
                addNewEnv().withName("MY_ENV").withValue("CHEESE").endEnv().
                build()));


        System.out.println(KubernetesHelper.toYaml(app));
        System.out.println();

        List<HasMetadata> resources = new AppGenerator().generateResources(app);


        System.out.println("Generated resources:");
        System.out.println();

        KubernetesList list = new KubernetesList();
        list.setItems(resources);
        System.out.println(KubernetesHelper.toYaml(list));

    }

}
