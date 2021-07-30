package org.bookmc.ink.extension;

import org.bookmc.ink.platform.Environment;
import org.gradle.api.Project;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;

public class InkExtension {
    public Property<String> runDirectory;
    public Property<String> gameVersion;
    public Property<String> tweakClass;
    public Property<Environment> platform;
    public Property<String> mappings;
    public Property<Boolean> makeObfSourceJar;
    public Property<Boolean> bleedingEdge;

    public InkExtension(Project project) {
        ObjectFactory objectFactory = project.getObjects();

        platform = objectFactory.property(Environment.class);
        platform.set(Environment.CLIENT);

        gameVersion = objectFactory.property(String.class);
        gameVersion.set("1.8.9");

        tweakClass = objectFactory.property(String.class);

        runDirectory = objectFactory.property(String.class);
        runDirectory.set("run");

        mappings = objectFactory.property(String.class);
        mappings.set("stable_22");

        makeObfSourceJar = objectFactory.property(Boolean.class);
        makeObfSourceJar.set(false);

        bleedingEdge = objectFactory.property(Boolean.class);
        bleedingEdge.set(false);
    }
}
