package org.example.agent;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;
import org.example.agent.buddy.TimeInterceptor;
import org.example.agent.instrument.PerformMonitorTransformer;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;

import static net.bytebuddy.matcher.ElementMatchers.*;
import static net.bytebuddy.matcher.ElementMatchers.named;

/**
 * https://www.pianshen.com/article/80921388272/ bytebuddy源码分析
 * https://www.programcreek.com/java-api-examples/?class=net.bytebuddy.matcher.ElementMatcher&method=Junction demo
 *
 */
public class SkywalkingAgent {

    public static void premain(String args, Instrumentation instrumentation) {
        System.out.println("this is an agent args is :" + args);


//        @Override
//        protected InstanceMethodsInterceptPoint[] getInstanceMethodsInterceptPoints() {
//            return new InstanceMethodsInterceptPoint[] {
//                    new InstanceMethodsInterceptPoint() {
//                        @Override
//                        public ElementMatcher<MethodDescription> getMethodsMatcher() {
//                            return named("invoke");
//                        }
//
//                        @Override
//                        public String getMethodsInterceptor() {
//                            return INVOKE_INTERCEPT_CLASS;
//                        }
//
//                        @Override
//                        public boolean isOverrideArgs() {
//                            return false;
//                        }
//                    },
//                    new InstanceMethodsInterceptPoint() {
//                        @Override public ElementMatcher<MethodDescription> getMethodsMatcher() {
//                            return named("exception");
//                        }
//
//                        @Override public String getMethodsInterceptor() {
//                            return EXCEPTION_INTERCEPT_CLASS;
//                        }
//
//                        @Override public boolean isOverrideArgs() {
//                            return false;
//                        }
//                    }
//            };
//        }


        /** method interceptor use in open trace */

        AgentBuilder.Transformer transformer = new AgentBuilder.Transformer() {
            @Override
            public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader) {

                // 制定拦截固定方法
//                builder =
//                        builder.method(not(isStatic()).and(instanceMethodsInterceptPoint.getMethodsMatcher()))
//                                .intercept(
//                                        MethodDelegation.withDefaultConfiguration()
//                                                .to(new InstMethodsInter(interceptor, classLoader))
//                                );

                return builder
                        // 任意方法拦截
                        .method(ElementMatchers.any())
                        // 委托拦截
                        .intercept(MethodDelegation.to(TimeInterceptor.class));
            }
        };

        AgentBuilder.Listener listener = new AgentBuilder.Listener() {
            @Override
            public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, DynamicType dynamicType) {

            }

            @Override
            public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule) {

            }

            @Override
            public void onError(String s, ClassLoader classLoader, JavaModule javaModule, Throwable throwable) {

            }

            @Override
            public void onComplete(String s, ClassLoader classLoader, JavaModule javaModule) {

            }
        };

        new AgentBuilder.Default().type(ElementMatchers.nameStartsWith("biz"))
                .transform(transformer)
                .with(listener)
                .installOn(instrumentation);
    }
}
