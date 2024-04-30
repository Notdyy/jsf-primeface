package my.example.service.qualifier;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

@Inherited
@Qualifier
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({
    ElementType.METHOD,
    ElementType.FIELD,
    ElementType.PARAMETER,
    ElementType.TYPE
    })
public @interface Repository {

    public static final String MEMORY = "memory";
    public static final String DATABASE = "database";
    
    String value();
}
