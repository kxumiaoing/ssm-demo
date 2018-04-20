package com.demo.ssm.mybatis.page;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

/**
 * Created by xumiao on 4/20/18.
 */
@Intercepts({@Signature(type = StatementHandler.class,method = "prepare",args = {Connection.class,Integer.class})})
public class PageInterceptor implements Interceptor{
    private String dialect;

    public void setDialect(String dialect) {
        this.dialect = dialect;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MetaObject metaStatementHandler = SystemMetaObject.forObject(invocation.getTarget());

        for (;metaStatementHandler.hasGetter("h");){
            metaStatementHandler = SystemMetaObject.forObject(metaStatementHandler.getValue("h"));
        }

        for (;metaStatementHandler.hasGetter("target");){
            metaStatementHandler = SystemMetaObject.forObject(metaStatementHandler.getValue("target"));
        }

        MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");

        if (mappedStatement.getId().endsWith("ByPage")){
            BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
            Page page = detectPageParameter(boundSql.getParameterObject());

            if (null == page){
                throw new NullPointerException("Page parameter is not exists.");
            }

            Long totalRecords = queryTotalRecords((Connection) invocation.getArgs()[0],mappedStatement,boundSql);
            String pageSql = generatePageSql(boundSql.getSql(),page);

            page.setTotalRecords(totalRecords);

            if (null != pageSql){
                metaStatementHandler.setValue("delegate.boundSql.sql",pageSql);
            }
        }

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler){
            return Plugin.wrap(target,this);
        }

        return target;
    }

    @Override
    public void setProperties(Properties properties) {
        String dialect = properties.getProperty("dialect");

        if (null != dialect && !"".equals(dialect)){
            this.dialect = dialect;
        }
    }

    private Page detectPageParameter(Object parameterObject){
        if (null == parameterObject){
            return null;
        }

        if (parameterObject instanceof Page){
            return (Page) parameterObject;
        }

        if (parameterObject instanceof Map){
            for (Object obj:((Map) parameterObject).values()){
                if (obj instanceof Page){
                    return (Page) obj;
                }
            }
        }

        return null;
    }

    private Long queryTotalRecords(Connection connection, MappedStatement mappedStatement, BoundSql boundSql){
        Long totalRecords = 0l;
        PreparedStatement pstate = null;
        ResultSet rs = null;
        String sql = "select count(1) from (" + boundSql.getSql() + ") temp";
        ParameterHandler parameterHandler = mappedStatement.getConfiguration().newParameterHandler(mappedStatement,boundSql.getParameterObject(),boundSql);

        try {
            pstate = connection.prepareStatement(sql);
            parameterHandler.setParameters(pstate);
            rs = pstate.executeQuery();
            totalRecords = null != rs && rs.next()? Long.parseLong(rs.getInt(1) + ""):totalRecords;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (null != pstate){
                try {
                    pstate.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (null != rs){
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return totalRecords;
    }

    private String generatePageSql(String sql,Page page){
        if (null != this.dialect && !"".equals(this.dialect)){
            StringBuilder pageSql = new StringBuilder();

            if ("mysql".equals(this.dialect)){
                pageSql.append(sql);
                pageSql.append(" limit ");
                pageSql.append(page.getStartIndex());
                pageSql.append(',');
                pageSql.append(page.getPageSize());
            } else {
                pageSql.append("select * from (select temp.*,rownum rn from (");
                pageSql.append(sql);
                pageSql.append(") temp where rownum <= ");
                pageSql.append(page.getEndIndex());
                pageSql.append(") where rn > ");
                pageSql.append(page.getStartIndex());
            }

            return pageSql.toString();
        }

        return null;
    }
}
