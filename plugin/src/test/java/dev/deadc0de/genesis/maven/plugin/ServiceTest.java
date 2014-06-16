package dev.deadc0de.genesis.maven.plugin;

import dev.deadc0de.genesis.ServiceDescriptor;
import java.util.Collections;
import org.junit.Assert;
import org.junit.Test;

public class ServiceTest {

    @Test(expected = IllegalStateException.class)
    public void mappingToServiceDescriptorWhenNameIsNullThrows() {
        new Service().toServiceDescriptor();
    }

    @Test
    public void whenConfigurationIsNullThenMappedServiceDescriptorHasAnEmptyOne() {
        final Service service = new Service();
        service.name = "service";
        final ServiceDescriptor serviceDescriptor = service.toServiceDescriptor();
        Assert.assertTrue(serviceDescriptor.configuration.isEmpty());
    }

    @Test
    public void whenConfigurationIsNonNullThenMappedServiceDescriptorHasTheSameConfiguration() {
        final Service service = new Service();
        service.name = "service";
        service.configuration = Collections.singletonMap("parameter", "value");
        final ServiceDescriptor serviceDescriptor = service.toServiceDescriptor();
        Assert.assertEquals(service.configuration, serviceDescriptor.configuration);
    }

    @Test
    public void whenCollaboratorsMapIsNullThenMappedServiceDescriptorHasAnEmptyOne() {
        final Service service = new Service();
        service.name = "service";
        final ServiceDescriptor serviceDescriptor = service.toServiceDescriptor();
        Assert.assertTrue(serviceDescriptor.collaborators.isEmpty());
    }

    @Test
    public void whenCollaboratorsMapIsNonNullThenMappedServiceDescriptorHasTheMappedCollaborators() {
        final Service service = new Service();
        service.name = "service";
        final Service.Collaborator collaborator = new Service.Collaborator();
        collaborator.role = "role";
        collaborator.service = new Service();
        collaborator.service.name = "collaborator";
        service.collaborators = new Service.Collaborator[]{collaborator};
        final ServiceDescriptor serviceDescriptor = service.toServiceDescriptor();
        Assert.assertEquals(collaborator.service.name, serviceDescriptor.collaborators.get(collaborator.role).name);
    }
}
