package dev.deadc0de.genesis.maven.plugin;

import dev.deadc0de.genesis.ServiceDescriptor;
import edu.emory.mathcs.backport.java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
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
        service.configuration = new Service.Parameter[]{new Service.Parameter() {
            {
                name = "parameter";
                values = new String[]{"value"};
            }
        }};
        final ServiceDescriptor serviceDescriptor = service.toServiceDescriptor();
        final Map<String, List<String>> expectedConfiguration = Collections.singletonMap("parameter", Collections.singletonList("value"));
        Assert.assertEquals(expectedConfiguration, serviceDescriptor.configuration);
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
        final Service.Collaborator collaborator = new Service.Collaborator() {
            {
                role = "role";
                services = new Service[]{new Service() {
                    {
                        name = "collaborator";
                    }
                }};
            }
        };
        final Service service = new Service() {
            {
                name = "service";
                collaborators = new Service.Collaborator[]{collaborator};
            }
        };
        final ServiceDescriptor serviceDescriptor = service.toServiceDescriptor();
        Assert.assertEquals(collaborator.services[0].name, serviceDescriptor.collaborators.get(collaborator.role).get(0).name);
    }
}
