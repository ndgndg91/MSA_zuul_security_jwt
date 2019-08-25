package com.ndgndg91.zullpractice;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

@Component
public class CustomPreZuulFilter extends ZuulFilter {

    private static final Logger log = LoggerFactory.getLogger(CustomPreZuulFilter.class);

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return PRE_DECORATION_FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        log.info("맨처음 들어 올 때 !");
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.addZuulRequestHeader("ndgndg91", "ZUUL-PRE-FILTER");
        return null;
    }
}
