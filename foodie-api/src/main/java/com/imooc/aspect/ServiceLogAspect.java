package com.imooc.aspect;

import com.imooc.controller.Hello;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceLogAspect {
    public static final Logger logger= LoggerFactory.getLogger(Hello.class);
    /**
     * AOP通知类型
     * 1.前置通知：在方法调用之前执行
     * 2.后置通知：在方法正常调用之后执行
     * 3.环绕通知：在方法调用之前和之后执行
     * 4.异常通知：在方法调用中发送异常执行
     * 5.最终通知：在方法调用之后执行
     */

    /**
     * 切面表达式
     * execution 代表所要执行的表达式主体
     * 第一处 * 代表方法的返回类型 *代表所有
     * 第二处 包
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(* com.imooc.service.impl)")
    public Object recordTimeLog(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("===方法开始执行  {}.{}===",joinPoint.getTarget().getClass(),joinPoint.getSignature().getName());

        //记录开始时间
        long begin  = System.currentTimeMillis();
        //执行目标方法
        Object result = joinPoint.proceed();
        //记录结束时间
        long end  = System.currentTimeMillis();
        //时间差
        long time = end - begin;

        if(time > 3000){
            logger.error("=====执行结束，耗时：{} 毫秒====",time);
        }else if(time > 2000){
            logger.warn("=====执行结束，耗时：{} 毫秒====",time);
        }else{
            logger.info("=====执行结束，耗时：{} 毫秒====",time);
        }

    }
}
