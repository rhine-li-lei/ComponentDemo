package com.lilei.beerouter_compiler;

import javax.annotation.processing.AbstractProcessor;

/**
 * Created by lilei
 * Date : 2018/9/25
 */

abstract class BaseProcessor extends AbstractProcessor {

    static String firstCharUpperCase(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }

    static class Constants {

        static final String ANNO_PKG = "com.lilei.beerouter_anno";

        static final String KEY_HOST_NAME = "host";

        static final String ROUTERTABLE = "RouterTable";

        // com.lilei.generated.annotation.RouteNode
        static final String ANNOTATION_TYPE_ROUTE_NODE = ANNO_PKG + ".RouteNode";
//    String ANNOTATION_TYPE_ROUTER = ANNO_PKG + ".annotation.Router";
//    String ANNOTATION_TYPE_AUTOWIRED = ANNO_PKG + ".annotation.Autowired";
//
//        String PREFIX_OF_LOGGER = "[Router-Anno-Compiler]-- ";
//
//         //System interface
//        String ACTIVITY = "android.app.Activity";
//    String FRAGMENT = "android.app.Fragment";
//    String FRAGMENT_V4 = "android.support.v4.app.Fragment";
//    String SERVICE = "android.app.Service";
//    String PARCELABLE = "android.os.Parcelable";
//
//        // Java type
//        String LANG = "java.lang";
//        String BYTE = LANG + ".Byte";
//        String SHORT = LANG + ".Short";
//        String INTEGER = LANG + ".Integer";
//        String LONG = LANG + ".Long";
//        String FLOAT = LANG + ".Float";
//        String DOUBEL = LANG + ".Double";
//        String BOOLEAN = LANG + ".Boolean";
//        String STRING = LANG + ".String";
//
//    String ISYRINGE = "com.luojilab.component.componentlib.router.ISyringe";
//
//    String JSON_SERVICE = "com.luojilab.component.componentlib.service.JsonService";
//
//    String BASECOMPROUTER = "com.luojilab.component.componentlib.router.ui.BaseCompRouter";
    }
}
