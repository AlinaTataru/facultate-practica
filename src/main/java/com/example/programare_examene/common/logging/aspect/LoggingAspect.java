package com.example.programare_examene.common.logging.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    private static final boolean logEverything = true;

    //@Around("execution(* com.example.programare_examene.resource.*(..)))")
    private Object profile(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String className = signature.getDeclaringType().getSimpleName();
        String methodName = signature.getName();
        StopWatch watch = new StopWatch();

        if (logEverything) {
            logger.info("=> " + className + "." + methodName + " - start");
        }
        watch.start();
        Object result = joinPoint.proceed();
        watch.stop();

        if (logEverything || watch.getTotalTimeMillis() > 10_000) {
            logger.info("=> " + className + "." + methodName + " - end (" + watch.getTotalTimeMillis() + " ms)");
        }

        return result;
    }
}
