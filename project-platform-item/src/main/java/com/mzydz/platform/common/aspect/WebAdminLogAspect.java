package com.mzydz.platform.common.aspect;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mzydz.platform.modules.user.entity.PO.AdminUser;
import com.mzydz.platform.modules.user.entity.PO.log.LogAdmin;
import com.mzydz.platform.modules.user.entity.enums.LogAdminAgentAppEnum;
import com.mzydz.platform.modules.user.service.IAdminUserService;
import com.mzydz.platform.modules.user.service.ILogAdminService;
import com.util.DateUtil;
import com.util.DealDateUtil;
import com.util.StringUtils;
import com.util.auth.AuthSign;
import com.util.request.HttpRequestUtil;
import config.annotation.LogMenthodName;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author kongling
 * @packge com.controller.aspect
 * @data 2019-01-09 14:03
 * @project Currency
 */
@Aspect
@Component
public class WebAdminLogAspect {
    // 特殊 请求记录日志处理
    /**
     * 后台登录
     */
    private static String adminUserLogin = "/admin/user/login";

    /**
     * 广告机管理后台登录
     */
    private static String agentUserLogin = "/agent/user/login";

    /**
     * 广告机注册
     */
    private static String agentUserregisterTel = "/agent/user/registerTel";


    private LogAdmin logAdmin;
    @Autowired
    private IAdminUserService adminUserService;
    @Autowired
    private ILogAdminService logAdminService;

    /**
     * 注入请求request 上下文
     */
    @Autowired
    private HttpServletRequest request;

    private Logger logger = LoggerFactory.getLogger(WebAdminLogAspect.class);
    /**
     * 计算时间
     */
    ThreadLocal<Long> startTime = new ThreadLocal<>();


    /**
     * 定义切入点，切入点为com.controller下的所有函数
     */
    //@Pointcut("execution(public * com.mzydz.platform.controller.*.*.*(..))")
    @Pointcut("@annotation(config.annotation.LogMenthodName)")
    public void webLog() {
    }

    /**
     * 前置通知：在连接点之前执行的通知
     *
     * @param joinPoint
     * @throws Throwable
     */
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        try {
            startTime.set(System.currentTimeMillis());
            // 接收到请求，记录请求内容
            Signature signature = joinPoint.getSignature();
            MethodSignature methodSignature = (MethodSignature) signature;
            Method method = methodSignature.getMethod();
            Object params[] = joinPoint.getArgs();
            //自定义注解的方法操作别名
            LogMenthodName logMenthodName = method.getAnnotation(LogMenthodName.class);
            //判断方法是否存在这个注解
            if (method.isAnnotationPresent(LogMenthodName.class)) {
                //日志保存数据库
                logAdmin = LogAdmin.builder()
                        .createTime(DealDateUtil.getNowDate())
                        .ip(HttpRequestUtil.getIpAddr(request))
                        .loginFacility(HttpRequestUtil.getSystemDevice(request))
                        .methodName(logMenthodName.name())
                        .methodUrl(signature.getDeclaringTypeName() + "." + signature.getName())
                        .requestUrl(request.getRequestURL().toString())
                        .requestWay(request.getMethod())
                        .type(logMenthodName.logType()).build();
                if (params.length == 0) {
                    logAdmin.setRequestParam("无参数");
                } else {
                    logAdmin.setRequestParam(Arrays.toString(params));
                }
            }
            // 记录下请求内容
            logger.info("------------------------------------------------------------------------------------------------");
            logger.info("请求地址: " + request.getRequestURL().toString());
            logger.info("请求方式: " + request.getMethod());
            logger.info("请求时间: " + DateUtil.getCurrentDate());
            logger.info("IP : " + HttpRequestUtil.getIpAddr(request));
            logger.info("方法地址 : " + signature.getDeclaringTypeName() + "." + signature.getName());
            if (logMenthodName == null) {
                logger.info("方法别名 : 未知");
            } else {
                logger.info("方法别名 : " + logMenthodName.name());
            }
            if (params.length == 0) {
                logger.info("请求参数 : 无参");
            } else {
                logger.info("请求参数 : " + Arrays.toString(params));
            }
        } catch (Exception e) {
            logger.info("日志记录错误：" + ExceptionUtils.getFullStackTrace(e));
        }

    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(JoinPoint joinPoint, Object ret) throws Throwable {
        try {
            Signature signature = joinPoint.getSignature();
            MethodSignature methodSignature = (MethodSignature) signature;
            Method method = methodSignature.getMethod();
            String time = (System.currentTimeMillis() - startTime.get()) + "ms";
            if (method.isAnnotationPresent(LogMenthodName.class)) {
                String token = request.getHeader(AuthSign.token);
                Integer systemRoleId = AuthSign.getSystemRoleId(token);
                //已经拥有登录 token 信息
                if (systemRoleId != null) {
                    Long id = AuthSign.getUserId(token);
                    //总管理后台处理日志
                    if (systemRoleId == LogAdminAgentAppEnum.ADMIN_SYS.getSystem().intValue()) {
                        logAdmin.setReturnParam(Arrays.asList(ret).toString());
                        logAdmin.setTime(time);
                        logAdmin.setOperatorId(id);
                        logAdmin.setLoginFacility(HttpRequestUtil.getSystemDevice(request));
                        logAdminService.save(logAdmin);
                        //广告机管理后台处理日志
                    } else {

                    }
                    //没有登录token 特殊请求接口
                } else {
                    String uri = request.getRequestURI();
                    //后台管理员登录
                    if (adminUserLogin.equalsIgnoreCase(uri)) {
                        String account = getParameterAccount();
                        if (StringUtils.isNotBlank(account)) {
                            AdminUser adminUser = adminUserService.getOne(new QueryWrapper<AdminUser>()
                                    .eq("account", account));
                            if (adminUser != null) {
                                logAdmin.setReturnParam(Arrays.asList(ret).toString());
                                logAdmin.setTime(time);
                                logAdmin.setOperatorId(adminUser.getId());
                                logAdmin.setLoginFacility(HttpRequestUtil.getSystemDevice(request));
                                logAdminService.save(logAdmin);
                            }
                        }
                    }
                }
            }
            // 处理完请求，返回内容
            // logger.info("返回内容: " + Arrays.asList(ret));
            logger.info("耗时 : " + time);
            startTime.remove();
        } catch (
                Exception e) {
            logger.info("日志记录错误：" + ExceptionUtils.getFullStackTrace(e));
        }

    }

    private String getParameterAccount() {
        String account = null;
        try {
            //得到想要的参数
            account = request.getParameter("account");
        } catch (Exception e) {
            logger.info("获取账户参数错误：" + ExceptionUtils.getFullStackTrace(e));
        }
        return account;
    }
}
