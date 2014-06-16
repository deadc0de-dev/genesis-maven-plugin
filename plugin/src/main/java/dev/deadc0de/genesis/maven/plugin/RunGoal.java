package dev.deadc0de.genesis.maven.plugin;

import dev.deadc0de.genesis.GenerationContext;
import dev.deadc0de.genesis.ServiceFactory;
import dev.deadc0de.genesis.module.ServiceModule;
import java.util.ServiceLoader;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = "run")
public class RunGoal extends AbstractMojo {

    @Parameter(required = true)
    private Service service;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (service == null || service.name == null) {
            throw new MojoExecutionException("root service name must be configured");
        }
        final Stream<ServiceFactory> serviceFactories = loadServiceFactories();
        final GenerationContext context = new GenerationContext(serviceFactories);
        final Runnable rootService = context.generate(Runnable.class, service.toServiceDescriptor());
        rootService.run();
    }

    private static Stream<ServiceFactory> loadServiceFactories() {
        final ServiceLoader<ServiceModule> loader = ServiceLoader.load(ServiceModule.class);
        final Stream<ServiceModule> serviceModules = StreamSupport.stream(loader.spliterator(), false);
        return serviceModules.flatMap(ServiceModule::assemble);
    }
}
