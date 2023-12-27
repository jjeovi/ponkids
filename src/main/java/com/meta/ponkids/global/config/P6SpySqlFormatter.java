package com.meta.ponkids.global.config;

import com.p6spy.engine.logging.Category;
import com.p6spy.engine.spy.P6SpyOptions;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import org.hibernate.engine.jdbc.internal.FormatStyle;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Locale;

/**
 * className    : P6SpySqlFormatter
 * author         : jjeoV
 * date           : 11/1/23
 * description    : p6spy 로그의 formatter 를 setting 하기 위한 class
 * p6spy 멀티라인 적용을 위해 작업한다.  ( https://shanepark.tistory.com/415 )
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 11/1/23        jjeoV       최초 생성
 */

@Configuration
public class P6SpySqlFormatter implements MessageFormattingStrategy {
    
    @PostConstruct
    public void setLogMessageFormat() {
        P6SpyOptions.getActiveInstance().setLogMessageFormat( this.getClass().getName() );
    }
    
    @Override
    public String formatMessage( int connectionId, String now, long elapsed, String category, String prepared, String sql, String url ) {
        sql = formatSql( category, sql );
        return String.format( "[%s] | %d ms | %s", category, elapsed, formatSql( category, sql ) );
    }
    
    private String formatSql( String category, String sql ) {
        if ( sql != null && !sql.trim().isEmpty() && Category.STATEMENT.getName().equals( category ) ) {
            String trimmedSQL = sql.trim().toLowerCase( Locale.ROOT );
            if ( trimmedSQL.startsWith( "create" ) || trimmedSQL.startsWith( "alter" ) || trimmedSQL.startsWith( "comment" ) ) {
                sql = FormatStyle.DDL.getFormatter().format( sql );
            } else {
                sql = FormatStyle.BASIC.getFormatter().format( sql );
            }
            return sql;
        }
        return sql;
    }
}
