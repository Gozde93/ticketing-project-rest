package com.cydeo.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    private String getUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SimpleKeycloakAccount userDetails = (SimpleKeycloakAccount) authentication.getDetails();
        return userDetails.getKeycloakSecurityContext().getToken().getPreferredUsername();
    }

    @Pointcut("execution(* com.cydeo.controller.ProjectController.*(..)) || execution(* com.cydeo.controller.TaskController.*(..)) ")
    public void anyProjectTaskControllerPC(){}

    @Before("anyProjectTaskControllerPC()")
    public void beforeAnyProjectAndTaskControllerAdvice(JoinPoint joinPoint){
        log.info("Before -> Method: {}, User: {}", joinPoint.getSignature().toShortString()
        ,getUsername());
    }

    @AfterReturning(pointcut = "anyProjectTaskControllerPC()", returning = "results")
    public void afterReturningAnyProjectAndTaskControllerAdvice(JoinPoint joinPoint,Object results){
        log.info("After Returning -> Method: {}, User: {}"
                , joinPoint.getSignature().toShortString()
                ,getUsername()
                ,results.toString());
    }

    @AfterThrowing(pointcut = "anyProjectTaskControllerPC()", throwing = "exception")
    public void afterReturningAnyProjectAndTaskControllerAdvice(JoinPoint joinPoint,Exception exception){
        log.info("After Returning -> Method: {}, User: {}"
                , joinPoint.getSignature().toShortString()
                ,getUsername()
                ,exception.toString());
    }







}
