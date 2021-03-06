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

package io.fabric8.maven.plugin.enricher;

import java.util.Map;

import io.fabric8.kubernetes.api.builder.Visitor;
import io.fabric8.kubernetes.api.model.ReplicationControllerSpecBuilder;
import io.fabric8.kubernetes.api.model.ServiceSpecBuilder;
import io.fabric8.kubernetes.api.model.extensions.*;
import io.fabric8.maven.enricher.api.Kind;

/**
 * @author roland
 * @since 02/05/16
 */
public abstract class SelectorVisitor<T> implements Visitor<T> {

    protected final EnricherManager enricher;

    public SelectorVisitor(EnricherManager enricher) {
        this.enricher = enricher;
    }

    abstract public void visit(T item);

    // ========================================================================

    static public class Service extends SelectorVisitor<ServiceSpecBuilder> {

        public Service(EnricherManager enricher) {
            super(enricher);
        }

        @Override
        public void visit(ServiceSpecBuilder item) {
            item.getSelector().putAll(enricher.extractSelector(Kind.SERVICE));
        }
    }

    static public class ReplicationController extends SelectorVisitor<ReplicationControllerSpecBuilder> {

        public ReplicationController(EnricherManager enricher) {
            super(enricher);
        }

        @Override
        public void visit(ReplicationControllerSpecBuilder item) {
            item.getSelector().putAll(enricher.extractSelector(Kind.REPLICATION_CONTROLLER));
        }
    }

    static public class ReplicaSet extends SelectorVisitor<ReplicaSetSpecBuilder> {

        public ReplicaSet(EnricherManager enricher) {
            super(enricher);
        }

        @Override
        public void visit(ReplicaSetSpecBuilder item) {
            item.withSelector(createLabelSelector(enricher.extractSelector(Kind.REPLICA_SET)));
        }

        private LabelSelector createLabelSelector(Map<String, String> labelSelector) {
            return new LabelSelectorBuilder().withMatchLabels(labelSelector).build();
        }
    }

}
