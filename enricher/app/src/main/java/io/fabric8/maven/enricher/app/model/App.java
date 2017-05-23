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
import io.fabric8.kubernetes.api.model.Container;
import io.fabric8.kubernetes.api.model.LabelSelector;
import io.fabric8.kubernetes.api.model.LocalObjectReference;
import io.fabric8.kubernetes.api.model.PodSecurityContext;
import io.fabric8.kubernetes.api.model.PodSpec;
import io.fabric8.kubernetes.api.model.ServiceSpec;
import io.fabric8.kubernetes.api.model.Volume;
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

    @JsonProperty("service")
    private ServiceSpec service;

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

    public void setPaused(Boolean paused) {
        this.paused = paused;
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

    // TODO workaround code generation issue
/*
    public Boolean getPaused() {
        return paused;
    }
*/

    public boolean isPaused() {
        return paused != null && paused.booleanValue();
    }


    // TODO lets expose base properties as a hack to get the builder code generator to work

    @Override
    public Long getActiveDeadlineSeconds() {
        // TODO
        return super.getActiveDeadlineSeconds();

    }

    @Override
    public void setActiveDeadlineSeconds(Long activeDeadlineSeconds) {
        // TODO
        super.setActiveDeadlineSeconds(activeDeadlineSeconds);

    }

    @Override
    public List<Container> getContainers() {
        // TODO
        return super.getContainers();

    }

    @Override
    public void setContainers(List<Container> containers) {
        // TODO
        super.setContainers(containers);

    }

    @Override
    public String getDnsPolicy() {
        // TODO
        return super.getDnsPolicy();

    }

    @Override
    public void setDnsPolicy(String dnsPolicy) {
        // TODO
        super.setDnsPolicy(dnsPolicy);

    }

    @Override
    public Boolean getHostIPC() {
        // TODO
        return super.getHostIPC();

    }

    @Override
    public void setHostIPC(Boolean hostIPC) {
        // TODO
        super.setHostIPC(hostIPC);

    }

    @Override
    public Boolean getHostNetwork() {
        // TODO
        return super.getHostNetwork();

    }

    @Override
    public void setHostNetwork(Boolean hostNetwork) {
        // TODO
        super.setHostNetwork(hostNetwork);

    }

    @Override
    public Boolean getHostPID() {
        // TODO
        return super.getHostPID();

    }

    @Override
    public void setHostPID(Boolean hostPID) {
        // TODO
        super.setHostPID(hostPID);

    }

    @Override
    public String getHostname() {
        // TODO
        return super.getHostname();

    }

    @Override
    public void setHostname(String hostname) {
        // TODO
        super.setHostname(hostname);

    }

    @Override
    public List<LocalObjectReference> getImagePullSecrets() {
        // TODO
        return super.getImagePullSecrets();

    }

    @Override
    public void setImagePullSecrets(List<LocalObjectReference> imagePullSecrets) {
        // TODO
        super.setImagePullSecrets(imagePullSecrets);

    }

    @Override
    public String getNodeName() {
        // TODO
        return super.getNodeName();

    }

    @Override
    public void setNodeName(String nodeName) {
        // TODO
        super.setNodeName(nodeName);

    }

    @Override
    public Map<String, String> getNodeSelector() {
        // TODO
        return super.getNodeSelector();

    }

    @Override
    public void setNodeSelector(Map<String, String> nodeSelector) {
        // TODO
        super.setNodeSelector(nodeSelector);

    }

    @Override
    public String getRestartPolicy() {
        // TODO
        return super.getRestartPolicy();

    }

    @Override
    public void setRestartPolicy(String restartPolicy) {
        // TODO
        super.setRestartPolicy(restartPolicy);

    }

    @Override
    public PodSecurityContext getSecurityContext() {
        // TODO
        return super.getSecurityContext();

    }

    @Override
    public void setSecurityContext(PodSecurityContext securityContext) {
        // TODO
        super.setSecurityContext(securityContext);

    }

    @Override
    public String getServiceAccount() {
        // TODO
        return super.getServiceAccount();

    }

    @Override
    public void setServiceAccount(String serviceAccount) {
        // TODO
        super.setServiceAccount(serviceAccount);

    }

    @Override
    public String getServiceAccountName() {
        // TODO
        return super.getServiceAccountName();

    }

    @Override
    public void setServiceAccountName(String serviceAccountName) {
        // TODO
        super.setServiceAccountName(serviceAccountName);

    }

    @Override
    public String getSubdomain() {
        // TODO
        return super.getSubdomain();

    }

    @Override
    public void setSubdomain(String subdomain) {
        // TODO
        super.setSubdomain(subdomain);

    }

    @Override
    public Long getTerminationGracePeriodSeconds() {
        // TODO
        return super.getTerminationGracePeriodSeconds();

    }

    @Override
    public void setTerminationGracePeriodSeconds(Long terminationGracePeriodSeconds) {
        // TODO
        super.setTerminationGracePeriodSeconds(terminationGracePeriodSeconds);

    }

    @Override
    public List<Volume> getVolumes() {
        // TODO
        return super.getVolumes();

    }

    @Override
    public void setVolumes(List<Volume> volumes) {
        // TODO
        super.setVolumes(volumes);

    }

    @Override
    public Map<String, Object> getAdditionalProperties() {
        // TODO
        return super.getAdditionalProperties();

    }

    @Override
    public void setAdditionalProperty(String name, Object value) {
        // TODO
        super.setAdditionalProperty(name, value);

    }
}
