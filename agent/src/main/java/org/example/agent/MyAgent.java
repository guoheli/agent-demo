package org.example.agent;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;
import org.example.agent.buddy.TimeInterceptor;
import org.example.agent.instrument.PerformMonitorTransformer;
import org.example.agent.jvm.Metric;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MyAgent {

    public static void premain(String args, Instrumentation instrumentation) {
        System.out.println("this is an agent args is :" + args);

        /** load jvm metric info **/
//        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {
//            public void run() {
//                Metric.printMemoryInfo();
//                Metric.printGCInfo();
//            }
//        }, 0, 60, TimeUnit.MILLISECONDS);

        /** use instrumentation add transformer */
        // 添加 Transformer
        ClassFileTransformer transformer = new PerformMonitorTransformer();
        instrumentation.addTransformer(transformer);

        /** method interceptor use in open trace */

//        AgentBuilder.Transformer transformer = new AgentBuilder.Transformer() {
//            @Override
//            public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader) {
//                return builder
//                        // 任意方法拦截
//                        .method(ElementMatchers.any())
//                        // 委托拦截
//                        .intercept(MethodDelegation.to(TimeInterceptor.class));
//            }
//        };
//
//        AgentBuilder.Listener listener = new AgentBuilder.Listener() {
//            @Override
//            public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, DynamicType dynamicType) {
//
//            }
//
//            @Override
//            public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule) {
//
//            }
//
//            @Override
//            public void onError(String s, ClassLoader classLoader, JavaModule javaModule, Throwable throwable) {
//
//            }
//
//            @Override
//            public void onComplete(String s, ClassLoader classLoader, JavaModule javaModule) {
//
//            }
//        };
//
//        new AgentBuilder.Default().type(ElementMatchers.nameStartsWith("biz"))
//                .transform(transformer)
//                .with(listener)
//                .installOn(instrumentation);
    }
}
