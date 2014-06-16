package dev.deadc0de.genesis.maven.plugin;

import dev.deadc0de.genesis.ServiceDescriptor;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Service {

    public String name;
    public Map<String, String> configuration;
    public Collaborator[] collaborators;

    public static class Collaborator {

        public String role;
        public Service service;
    }

    public ServiceDescriptor toServiceDescriptor() {
        if (name == null) {
            throw new IllegalStateException("service name must be specified");
        }
        final Map<String, String> serviceConfiguration = configuration == null ? Collections.emptyMap() : configuration;
        final Map<String, ServiceDescriptor> serviceCollaborators = new HashMap<>();
        if (collaborators != null) {
            for (Collaborator collaborator : collaborators) {
                serviceCollaborators.put(collaborator.role, collaborator.service.toServiceDescriptor());
            }
        }
        return new ServiceDescriptor(name, serviceConfiguration, serviceCollaborators);
    }
}
