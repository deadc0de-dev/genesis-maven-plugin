package dev.deadc0de.genesis.maven.plugin;

import dev.deadc0de.genesis.ServiceDescriptor;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Service {

    public String name;
    public Parameter[] configuration;
    public Collaborator[] collaborators;

    public static class Parameter {

        public String name;
        public String[] values;
    }

    public static class Collaborator {

        public String role;
        public Service[] services;
    }

    public ServiceDescriptor toServiceDescriptor() {
        if (name == null) {
            throw new IllegalStateException("service name must be specified");
        }
        final Map<String, List<String>> serviceConfiguration = configuration == null
                ? Collections.emptyMap()
                : Stream.of(configuration).collect(Collectors.toMap(
                                parameter -> parameter.name,
                                parameter -> Arrays.asList(parameter.values)));
        final Map<String, List<ServiceDescriptor>> serviceCollaborators = collaborators == null
                ? Collections.emptyMap()
                : Stream.of(collaborators).collect(Collectors.toMap(
                                collaborator -> collaborator.role,
                                collaborator -> Stream.of(collaborator.services).map(Service::toServiceDescriptor).collect(Collectors.toList())));
        return new ServiceDescriptor(name, serviceConfiguration, serviceCollaborators);
    }
}
