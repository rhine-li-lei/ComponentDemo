package com.lilei.router_compiler;

import com.google.auto.service.AutoService;
import com.lilei.router_annotation.RouterNode;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

@AutoService(Processor.class)
public class RouterProcessor extends AbstractProcessor{
    private Filer mFiler;   // 注解处理器创建文件的File工具
    private Elements mElementUtils; // 相关元素处理工具
    private Elements elements;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mFiler = processingEnv.getFiler();
        elements = processingEnv.getElementUtils();
        mElementUtils = processingEnv.getElementUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        gen();
        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(RouterNode.class.getCanonicalName());
        return types;
    }


    public void gen(){

        // superClassName
        ClassName superClass = ClassName.get(elements.getTypeElement("com.lilei.router_api.IComponentRouter"));

        MethodSpec main = MethodSpec
                .methodBuilder("test")
                .addModifiers(Modifier.PUBLIC)
                .addStatement("$T.out.println($S)", System.class, "hello world")
                .build();
        TypeSpec hello = TypeSpec.classBuilder("HelloWorld")
                .addModifiers(Modifier.PUBLIC)
//                .superclass(superClass)
                .addSuperinterface(superClass)
                .addMethod(main)
                .build();

        String packgeName = "com.lilei.gen";
        JavaFile file = JavaFile.builder(packgeName, hello).build();
        try {
            file.writeTo(mFiler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
