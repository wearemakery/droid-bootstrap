package android.widget;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

@Qualifier
@Documented
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Font {
  String value() default "";
}
