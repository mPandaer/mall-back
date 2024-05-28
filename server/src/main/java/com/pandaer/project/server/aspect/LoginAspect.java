package com.pandaer.project.server.aspect;

import cn.hutool.core.convert.NumberWithFormat;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.pandaer.project.base.exception.BusinessException;
import com.pandaer.project.server.aspect.annotation.NotLogin;
import com.pandaer.project.server.common.jwt.JwtProvider;
import com.pandaer.project.server.util.LoginIdUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;


/**
 * 登录增强切面
 */
@Aspect
@Component
public class LoginAspect {

    /**
     * 校验AccessToken,并解析
     * 获取userId,将其存入ThreadLocal中
     * 方便下游使用
     */
    @Pointcut("execution(* com.pandaer.project.server.modules..*.controller..*.*(..))")
    public void pointCut() {
    }

    @Resource
    private HttpServletRequest request;

    @Resource
    private JwtProvider jwtProvider;

    @Around("pointCut()")
    public Object checkLogin(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        NotLogin annotation = signature.getMethod().getAnnotation(NotLogin.class);
        if (ObjectUtil.isNotNull(annotation)) {
            return pjp.proceed();
        }else {
            String authorization = "Authorization";
            String auth = request.getHeader(authorization);
            if (StrUtil.isEmpty(auth)) {
                auth = request.getParameter(authorization);
            }
            if (StrUtil.isEmpty(auth)) {
                throw new BusinessException(400, "用户未登录");
            }
            //验证Token
            if (!jwtProvider.validateToken(auth)) {
                throw new BusinessException(401, "用户登录凭证过期!");
            }

            try {
                //取出登录的信息并保存到ThreadLocal中
                NumberWithFormat numberWithFormat = (NumberWithFormat) jwtProvider.getPayloadBy(auth, "userId");
                long userId = numberWithFormat.longValue();
                LoginIdUtil.setUserId(userId);
                return pjp.proceed();
            } catch (Exception e) {
                throw new BusinessException(500,"系统内部异常: checkLogin");
            }
        }

    }
}
