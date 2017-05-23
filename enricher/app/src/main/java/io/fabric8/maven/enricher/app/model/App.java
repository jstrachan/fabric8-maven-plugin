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
package io.fabric8.maven.enricher.app.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.fabric8.kubernetes.api.model.LabelSelector;
import io.fabric8.kubernetes.api.model.PersistentVolumeClaimSpec;
import io.fabric8.kubernetes.api.model.PodSpec;
import io.fabric8.kubernetes.api.model.ServiceSpec;
import io.fabric8.kubernetes.api.model.extensions.DeploymentStrategy;
import io.fabric8.kubernetes.api.model.extensions.RollbackConfig;
import io.sundr.builder.annotations.Buildable;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represents an application
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({"name", "namespace", "labels", "annotations", "replicas", "containers", "strategy", "selector", "service"})
@Buildable(builderPackage = "io.fabric8.kubernetes.api.builder", editableEnabled = false)
public class App extends PodSpec {

    // From ObjectMeta
    @JsonProperty("annotations")
    @Valid
    private Map<String, String> annotations;
    @JsonProperty("clusterName")
    private String clusterName;
    @JsonProperty("deletionGracePeriodSeconds")
    private Long deletionGracePeriodSeconds;
    @JsonProperty("finalizers")
    @Valid
    private List<String> finalizers = new ArrayList();
    @JsonProperty("labels")
    @Valid
    private Map<String, String> labels;
    @JsonProperty("name")
    private String name;
    @JsonProperty("namespace")
    @Pattern(
            regexp = "^[a-z0-9]([-a-z0-9]*[a-z0-9])?(\\.[a-z0-9]([-a-z0-9]*[a-z0-9])?)*$"
    )
    @Size(
            max = 253
    )
    private String namespace;


    // from DeploymentSpec
    @JsonProperty("minReadySeconds")
    private Integer minReadySeconds;
    @JsonProperty("paused")
    private Boolean paused;
    @JsonProperty("progressDeadlineSeconds")
    private Integer progressDeadlineSeconds;
    @JsonProperty("replicas")
    private Integer replicas;
    @JsonProperty("revisionHistoryLimit")
    private Integer revisionHistoryLimit;
    @JsonProperty("rollbackTo")
    @Valid
    private RollbackConfig rollbackTo;
    @JsonProperty("selector")
    @Valid
    private LabelSelector selector;
    @JsonProperty("strategy")
    @Valid
    private DeploymentStrategy strategy;


    // optional Service spec
    @JsonProperty("service")
    private ServiceSpec service;

    // optional ConfigMap data
    @JsonProperty("configData")
    @Valid
    private Map<String, String> configData;


    // optional PVC data
    @JsonProperty("persistentVolumes")
    @Valid
    private Map<String, PersistentVolumeClaimSpec> persistentVolumes;


    public App() {
    }

    public Map<String, String> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(Map<String, String> annotations) {
        this.annotations = annotations;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public Long getDeletionGracePeriodSeconds() {
        return deletionGracePeriodSeconds;
    }

    public void setDeletionGracePeriodSeconds(Long deletionGracePeriodSeconds) {
        this.deletionGracePeriodSeconds = deletionGracePeriodSeconds;
    }

    public List<String> getFinalizers() {
        return finalizers;
    }

    public void setFinalizers(List<String> finalizers) {
        this.finalizers = finalizers;
    }

    public Map<String, String> getLabels() {
        return labels;
    }

    public void setLabels(Map<String, String> labels) {
        this.labels = labels;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public Integer getMinReadySeconds() {
        return minReadySeconds;
    }

    public void setMinReadySeconds(Integer minReadySeconds) {
        this.minReadySeconds = minReadySeconds;
    }

    public Integer getProgressDeadlineSeconds() {
        return progressDeadlineSeconds;
    }

    public void setProgressDeadlineSeconds(Integer progressDeadlineSeconds) {
        this.progressDeadlineSeconds = progressDeadlineSeconds;
    }

    public Integer getReplicas() {
        return replicas;
    }

    public void setReplicas(Integer replicas) {
        this.replicas = replicas;
    }

    public Integer getRevisionHistoryLimit() {
        return revisionHistoryLimit;
    }

    public void setRevisionHistoryLimit(Integer revisionHistoryLimit) {
        this.revisionHistoryLimit = revisionHistoryLimit;
    }

    public RollbackConfig getRollbackTo() {
        return rollbackTo;
    }

    public void setRollbackTo(RollbackConfig rollbackTo) {
        this.rollbackTo = rollbackTo;
    }

    public LabelSelector getSelector() {
        return selector;
    }

    public void setSelector(LabelSelector selector) {
        this.selector = selector;
    }

    public DeploymentStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(DeploymentStrategy strategy) {
        this.strategy = strategy;
    }

    public ServiceSpec getService() {
        return service;
    }

    public void setService(ServiceSpec service) {
        this.service = service;
    }

    public Map<String, String> getConfigData() {
        return configData;
    }

    public void setConfigData(Map<String, String> configData) {
        this.configData = configData;
    }

    public Map<String, PersistentVolumeClaimSpec> getPersistentVolumes() {
        return persistentVolumes;
    }

    public void setPersistentVolumes(Map<String, PersistentVolumeClaimSpec> persistentVolumes) {
        this.persistentVolumes = persistentVolumes;
    }

    public boolean isPaused() {
        return paused != null && paused.booleanValue();
    }

    // TODO workaround code generation issue
/*
    public Boolean getPaused() {
        return paused;
    }
*/

    public void setPaused(Boolean paused) {
        this.paused = paused;
    }


}
