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

import io.fabric8.kubernetes.api.model.ConfigMapBuilder;
import io.fabric8.kubernetes.api.model.HasMetadata;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.api.model.ObjectMetaBuilder;
import io.fabric8.kubernetes.api.model.PersistentVolumeClaimBuilder;
import io.fabric8.kubernetes.api.model.PersistentVolumeClaimSpec;
import io.fabric8.kubernetes.api.model.ServiceBuilder;
import io.fabric8.kubernetes.api.model.ServiceSpec;
import io.fabric8.kubernetes.api.model.Volume;
import io.fabric8.kubernetes.api.model.VolumeBuilder;
import io.fabric8.kubernetes.api.model.extensions.DeploymentBuilder;
import io.fabric8.maven.enricher.app.model.App;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 */
public class AppGenerator {

    /**
     * Returns the Kubernetes resources for the given App
     */
    public List<HasMetadata> generateResources(App app) {
        List<HasMetadata> answer = new ArrayList<>();

        List<Volume> volumes = app.getVolumes();

        Map<String, PersistentVolumeClaimSpec> persistentVolumes = app.getPersistentVolumes();
        if (persistentVolumes != null) {
            Set<Map.Entry<String, PersistentVolumeClaimSpec>> entries = persistentVolumes.entrySet();
            for (Map.Entry<String, PersistentVolumeClaimSpec> entry : entries) {
                String name = entry.getKey();
                PersistentVolumeClaimSpec persistentVolumeClaimSpec = entry.getValue();
                lazyCreateVolume(volumes, name, persistentVolumeClaimSpec);
                ObjectMeta metadata = createMetadata(app);
                metadata.setName(name);
                answer.add(new PersistentVolumeClaimBuilder().
                        withMetadata(metadata).
                        withSpec(persistentVolumeClaimSpec).
                        build());
            }
        }

        answer.add(new DeploymentBuilder().
                withMetadata(createMetadata(app)).
                withNewSpec().
                withMinReadySeconds(app.getMinReadySeconds()).
                withPaused(app.isPaused()).
                withProgressDeadlineSeconds(app.getProgressDeadlineSeconds()).
                withReplicas(app.getReplicas()).
                withRevisionHistoryLimit(app.getRevisionHistoryLimit()).
                withRollbackTo(app.getRollbackTo()).
                withSelector(app.getSelector()).
                withNewTemplate().
                withMetadata(createMetadata(app)).
                withSpec(app).
                endTemplate().
                endSpec().
                build());

        ServiceSpec service = app.getService();
        if (service != null) {
            answer.add(new ServiceBuilder().
                    withMetadata(createMetadata(app)).withSpec(service).
                    build());
        }
        Map<String, String> configData = app.getConfigData();
        if (configData != null && !configData.isEmpty()) {
            answer.add(new ConfigMapBuilder().
                    withMetadata(createMetadata(app)).withData(configData).
                    build());
        }

        // TODO add volumes and configmaps
        return answer;
    }

    private void lazyCreateVolume(List<Volume> volumes, String pvcName, PersistentVolumeClaimSpec persistentVolumeClaimSpec) {
        for (Volume volume : volumes) {
            String name = volume.getName();
            if (pvcName.equals(name)) {
                return;
            }
        }
        volumes.add(new VolumeBuilder().withName(pvcName).withNewPersistentVolumeClaim().withClaimName(pvcName).endPersistentVolumeClaim().build());
    }

    public ObjectMeta createMetadata(App app) {
        return new ObjectMetaBuilder().
                withName(app.getName()).
                withNamespace(app.getNamespace()).
                withLabels(app.getLabels()).
                withAnnotations(app.getAnnotations()).
                withClusterName(app.getClusterName()).
                withFinalizers(app.getFinalizers()).
                build();
    }
}
