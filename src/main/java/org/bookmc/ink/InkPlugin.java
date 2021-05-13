package org.bookmc.ink;

import net.minecraftforge.gradle.user.tweakers.ClientTweaker;
import net.minecraftforge.gradle.user.tweakers.TweakerExtension;
import org.bookmc.ink.utils.GithubUtils;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.dsl.RepositoryHandler;

import javax.annotation.Nonnull;

public class InkPlugin implements Plugin<Project> {
    private final String[] repositories = new String[]{"https://jitpack.io", "https://maven.minecraftforge.net/"};

    @Override
    public void apply(@Nonnull Project project) {
        // Apply repositories
        RepositoryHandler repositoryHandler = project.getRepositories();
        for (String repository : repositories) {
            repositoryHandler.maven(maven -> maven.setUrl(repository));
        }

        project.getPluginManager().apply(ClientTweaker.class);

        TweakerExtension extension = project.getExtensions().getByType(TweakerExtension.class);
        extension.setVersion("1.8.9");
        extension.setTweakClass("org.bookmc.loader.BookMCLoader");
        extension.setMappings("stable_22");
        extension.setRunDir("run");

        project.getDependencies().add("implementation", "com.github.BookMC:modding:" + GithubUtils.getLatestCommit("BookMC/modding"));
        project.getDependencies().add("implementation", "com.github.BookMC:loader:" + GithubUtils.getLatestCommit("BookMC/loader"));
        project.getDependencies().add("implementation", "com.github.BookMC.minecraft:client:" + GithubUtils.getLatestCommit("BookMC/minecraft"));
    }
}
