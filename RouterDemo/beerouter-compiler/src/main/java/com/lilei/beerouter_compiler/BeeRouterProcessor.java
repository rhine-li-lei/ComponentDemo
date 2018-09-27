package com.lilei.beerouter_compiler;

import com.google.auto.service.AutoService;
import com.lilei.beerouter_anno.RouteNode;
import com.lilei.beerouter_compiler.utils.CollectionsUtil;
import com.lilei.beerouter_compiler.utils.FileUtils;
import com.lilei.beerouter_compiler.utils.Logger;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

import wrapper.Node;
import wrapper.NodeType;

import static com.lilei.beerouter_compiler.BaseProcessor.Constants.ANNOTATION_TYPE_ROUTE_NODE;
import static com.lilei.beerouter_compiler.BaseProcessor.Constants.KEY_HOST_NAME;
import static com.lilei.beerouter_compiler.BaseProcessor.Constants.ROUTERTABLE;

@AutoService(Processor.class)
@SupportedOptions(KEY_HOST_NAME)
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedAnnotationTypes({ANNOTATION_TYPE_ROUTE_NODE})
public class BeeRouterProcessor extends BaseProcessor{

    private Filer mFiler;
    private Elements mElementUtils;
    private Elements elements;
    private Logger logger;
    private String host = null;

    private List<Node> routeNodes = new ArrayList<Node>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mFiler = processingEnv.getFiler();
        elements = processingEnv.getElementUtils();
        mElementUtils = processingEnv.getElementUtils();
        logger = new Logger(processingEnvironment.getMessager());

        /**
         * init host
         */
        Map<String, String> options = processingEnv.getOptions();
        if (CollectionsUtil.isNotEmptyMap(options)) {
            host = options.get(KEY_HOST_NAME);
            logger.info(">>> host is " + host + " <<<");
        }
        if (host == null || host.equals("")) {
            logger.error("These no host name, at 'build.gradle', like :\n" +
                    "        javaCompileOptions {\n" +
                    "            annotationProcessorOptions {\n" +
                    "                arguments = [host: \"app\"]\n" +
                    "            }\n" +
                    "        }");
            throw new RuntimeException("BeeRouter::Compiler >>> No host name, for more information, look at gradle log.");
        }

    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        Set<? extends Element> routeNodesRaw = roundEnvironment.getElementsAnnotatedWith(RouteNode.class);

        for (Element nodeRaw : routeNodesRaw) {
            logger.info("route node ----> "+nodeRaw.getSimpleName());
            RouteNode routeAnno = nodeRaw.getAnnotation(RouteNode.class);
            logger.info("route node ----> path:"+routeAnno.path()+"    desc:"+routeAnno.desc());

            Node node = new Node();
            node.setNodeType(NodeType.ACTIVITY);
            node.setTarget(nodeRaw);
            node.setHost(host);
            node.setPath(routeAnno.path());
            node.setDesc(routeAnno.desc());
            node.setPriority(routeAnno.priority());
            routeNodes.add(node);
        }

        generateRouterTable(routeNodes);
        generateRouteNode(routeNodes);
        return true;
    }

    private void generateRouterTable(List<Node> routeNodes) {
        // ./UIRouterTable/{$host}RouterTable.txt
        String fileName = "./UIRouterTable/" + firstCharUpperCase(host) + ROUTERTABLE + ".txt";
        if (FileUtils.createFile(fileName)) {

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("auto generated, do not change !!!! \n\n");
            stringBuilder.append("HOST : ").append(host).append("\n\n");
            stringBuilder.append("path:"+"\t"+"desc"+"\n");
            for (Node node : routeNodes) {
                stringBuilder.append(node.getPath()).append("\t");
                stringBuilder.append(node.getDesc()).append("\n");
                stringBuilder.append("\n");
            }
            logger.info("------------------------------------------>"+stringBuilder+routeNodes.size());
            FileUtils.writeStringToFile(fileName, stringBuilder.toString(), false);
        }
    }

    private void generateRouteNode(List<Node> routeNodes) {

        String packgeName = "com.lilei.gen";
        // superClassName
        ClassName superClass = ClassName.get(elements.getTypeElement("com.lilei.beerouter_api.UIRouter.BaseCompRouter"));
        String className = firstCharUpperCase(host)+"$Router";

        MethodSpec.Builder initMapBuilder = MethodSpec
                .methodBuilder("initMap")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addCode("super.initMap();\n");
        for (Node node :routeNodes) {
            initMapBuilder.addCode("routeMapper.put($S,$T.class);\n",node.getPath(),node.getTarget());
        }
        MethodSpec initMap = initMapBuilder.build();

        MethodSpec getHost = MethodSpec
                .methodBuilder("getHost")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(String.class)
                .addStatement("return $S", host)
                .build();

        TypeSpec hello = TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC)
                .superclass(superClass)
                .addMethod(initMap)
                .addMethod(getHost)
                .build();

        JavaFile file = JavaFile.builder(packgeName, hello).build();
        try {
            file.writeTo(mFiler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
