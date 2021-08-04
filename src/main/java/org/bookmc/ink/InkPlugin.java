package org.bookmc.ink;

import net.minecraftforge.gradle.user.tweakers.ClientTweaker;
import net.minecraftforge.gradle.user.tweakers.ServerTweaker;
import net.minecraftforge.gradle.user.tweakers.TweakerExtension;
import org.bookmc.ink.constants.Constants;
import org.bookmc.ink.extension.InkExtension;
import org.bookmc.ink.platform.Environment;
import org.bookmc.ink.utils.GithubUtils;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.dsl.RepositoryHandler;

import javax.annotation.Nonnull;

public class InkPlugin implements Plugin<Project> {
    private final String[] repositories = new String[]{"https://jitpack.io", "https://maven.minecraftforge.net/", "https://repo.spongepowered.org/maven"};

    @Override
    public void apply(@Nonnull Project project) {
        // Apply repositories
        RepositoryHandler repositoryHandler = project.getRepositories();
        for (String repository : repositories) {
            repositoryHandler.maven(maven -> maven.setUrl(repository));
        }

        project.getPluginManager().apply(ClientTweaker.class);

        project.getExtensions().add(Constants.CONFIGURATION, new InkExtension(project));

        InkExtension inkExtension = project.getExtensions().getByType(InkExtension.class);

        TweakerExtension extension = project.getExtensions().getByType(TweakerExtension.class);
        Environment environment = project.getExtensions().getByType(InkExtension.class).platform.get();
        boolean isClient = environment == Environment.CLIENT;

        extension.setMappings(inkExtension.mappings.get());
        extension.setVersion(inkExtension.gameVersion.get());
        extension.setRunDir(inkExtension.runDirectory.get());
        extension.setMakeObfSourceJar(inkExtension.makeObfSourceJar.get());
        extension.setLaunchwrapper(false);
        extension.setMainClass(Constants.MAIN_CLASS);

        Configuration modDependency = project.getConfigurations().create(Constants.MOD_DEPENDENCY);
        project.getConfigurations().getByName(Constants.IMPLEMENTATION).extendsFrom(modDependency);

        project.getDependencies().add(Constants.MOD_DEPENDENCY, Constants.GROUP_ID + ":book-loader:" + GithubUtils.getLatestRelease("BookMC/loader", inkExtension.bleedingEdge.get()));
        project.getDependencies().add(Constants.MOD_DEPENDENCY, Constants.GROUP_ID + ".minecraft:" + (isClient ? "client" : "server") + ":" + GithubUtils.getLatestRelease("BookMC/minecraft", inkExtension.bleedingEdge.get()));
        project.getDependencies().add(Constants.ANNOTATION_PROCESSOR, "org.spongepowered:mixin:0.7.11-SNAPSHOT");

        project.getPluginManager().apply(environment == Environment.CLIENT ? ClientTweaker.class : ServerTweaker.class);
    }
}
