package presentation;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.spring5.dialect.SpringStandardDialect;
import org.thymeleaf.testing.templateengine.context.web.SpringWebProcessingContextBuilder;
import org.thymeleaf.testing.templateengine.engine.TestExecutor;
import org.thymeleaf.testing.templateengine.resolver.ITestableResolver;

import java.util.ArrayList;
import java.util.List;

public class FirstTest {

    @Test
    public void firstTest(){
        final List<IDialect> dialects = new ArrayList<IDialect>();
        dialects.add(new SpringStandardDialect());

        final SpringWebProcessingContextBuilder springPCBuilder = new SpringWebProcessingContextBuilder();

        final TestExecutor executor = new TestExecutor();
        executor.setProcessingContextBuilder(springPCBuilder);
        executor.setDialects(dialects);

        //executor.execute("mytestset");
        Assertions.assertTrue(executor.isAllOK());
    }
}
