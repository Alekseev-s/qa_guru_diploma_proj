package allure;

import java.lang.annotation.*;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@AutomationQA("Alekseev-s")
@ManualQA("Alekseev-s")
public @interface SuperTeam {
    
}
