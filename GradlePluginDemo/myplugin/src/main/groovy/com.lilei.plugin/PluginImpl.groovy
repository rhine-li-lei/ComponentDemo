package com.lilei.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

public class PluginImpl implements Plugin<Project> {

    @Override
    public void apply(Project project) {

        System.out.println("hello world!!")
    }
}
