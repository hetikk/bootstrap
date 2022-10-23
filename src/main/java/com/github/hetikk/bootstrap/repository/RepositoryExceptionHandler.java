package com.github.hetikk.bootstrap.repository;

import com.github.hetikk.bootstrap.common.exception.DataAccessException;
import com.github.hetikk.bootstrap.common.model.ErrorDetails;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Slf4j
@Component
@Aspect
public class RepositoryExceptionHandler {

    @Pointcut("within(@org.springframework.stereotype.Repository *)")
    public void repository() {
    }

    @Pointcut("execution(public * *(..))")
    public void publicMethod() {
    }

    @Around("repository() && publicMethod()")
    public Object handle(ProceedingJoinPoint pjp) {
        try {
            return pjp.proceed();
        } catch (Throwable t) {
            var rootCause = ExceptionUtils.getRootCause(t);
            ErrorDetails details = convert(pjp, rootCause);
            log.error("Data access error. " + details.message);
            throw new DataAccessException(t);
        }
    }

    private ErrorDetails convert(ProceedingJoinPoint pjp, Throwable rootCause) {
        ErrorDetails details = new ErrorDetails();
        details.cause = rootCause;

        if (rootCause instanceof javax.persistence.EntityNotFoundException) {
            var cause = ExceptionUtils.getRootCause(rootCause);
            details.message = isNull(cause) ? "Entity not found" : cause.getMessage();
        }

        return details;
    }

}
