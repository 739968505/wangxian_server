package com.xuanzhi.tools.gamestat;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Target({FIELD})
@Retention(RUNTIME)

public @interface Distribute {
	  /**
	   * 分布的取值
	   * @return
	   */
	  
	  public java.lang.String[] members() default {};
	  
	//设置分母
	 public String denominator() default "";
}