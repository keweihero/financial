package com.powderblue.manager.error;

import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ErrorViewResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author powderblue
 * 自定义错误处理Controller
 */
public class MyErrorController extends BasicErrorController {

    public MyErrorController(ErrorAttributes errorAttributes, ErrorProperties errorProperties, List<ErrorViewResolver> errorViewResolvers) {
        super(errorAttributes, errorProperties, errorViewResolvers);
    }

    @Override
    protected Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(request, includeStackTrace);
        errorAttributes.remove("timestamp");
        errorAttributes.remove("status");
        errorAttributes.remove("error");
        errorAttributes.remove("exception");
        errorAttributes.remove("path");

        String message = (String) errorAttributes.get("message");
        System.out.println("message:" + message);
        ErrorEnum errorEnum = ErrorEnum.getByCode(message);
        errorAttributes.put("message", errorEnum.getMessage());
        errorAttributes.put("code", errorEnum.getCode());
        errorAttributes.put("canRetry", errorEnum.isCanRetry());

        // TODO
        return errorAttributes;
    }
}
