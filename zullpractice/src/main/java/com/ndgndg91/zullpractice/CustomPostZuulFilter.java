package com.ndgndg91.zullpractice;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.POST_TYPE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SEND_RESPONSE_FILTER_ORDER;

@Component
public class CustomPostZuulFilter extends ZuulFilter {

    private static final Logger log = LoggerFactory.getLogger(CustomPostZuulFilter.class);

    @Override
    public String filterType() {
        return POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return SEND_RESPONSE_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        log.info("맨 마지막 일 듯?");
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletResponse res = ctx.getResponse();
        res.addHeader("ZUUL-POST-FILTER", "DongGil!");
        return null;
    }
}
