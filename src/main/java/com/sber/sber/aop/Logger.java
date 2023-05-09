package com.sber.sber.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class Logger {

    @Before("execution(public * allHuman())")
    public void getAdvice() {
        log.info("Попытка получения списка людей");
    }
}
