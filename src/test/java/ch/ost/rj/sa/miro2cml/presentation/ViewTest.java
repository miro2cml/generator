package ch.ost.rj.sa.miro2cml.presentation;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.spring5.dialect.SpringStandardDialect;
import org.thymeleaf.testing.templateengine.context.web.SpringWebProcessingContextBuilder;
import org.thymeleaf.testing.templateengine.engine.TestExecutor;

import java.util.ArrayList;
import java.util.List;

public class ViewTest {

    @Test
    public void GetHeaderIncFragmentTest() {
        final List<IDialect> dialects = new ArrayList<IDialect>();
        final SpringWebProcessingContextBuilder springPCBuilder = new SpringWebProcessingContextBuilder();
        final TestExecutor executor = new TestExecutor();

        dialects.add(new SpringStandardDialect());
        springPCBuilder.setApplicationContextConfigLocation(null);

        executor.setProcessingContextBuilder(springPCBuilder);
        executor.setDialects(dialects);

        executor.execute("classpath:tests/getHeaderIncFragment.thtest");
        Assertions.assertTrue(executor.isAllOK());
    }
    @Test
    public void GetHeaderFragmentTest() {
        final List<IDialect> dialects = new ArrayList<IDialect>();
        final SpringWebProcessingContextBuilder springPCBuilder = new SpringWebProcessingContextBuilder();
        final TestExecutor executor = new TestExecutor();

        dialects.add(new SpringStandardDialect());
        springPCBuilder.setApplicationContextConfigLocation(null);

        executor.setProcessingContextBuilder(springPCBuilder);
        executor.setDialects(dialects);

        executor.execute("classpath:tests/getHeaderFragment.thtest");
        Assertions.assertTrue(executor.isAllOK());
    }
    @Test
    public void GetBoardSuccessTest() {
        final List<IDialect> dialects = new ArrayList<IDialect>();
        final SpringWebProcessingContextBuilder springPCBuilder = new SpringWebProcessingContextBuilder();
        final TestExecutor executor = new TestExecutor();

        dialects.add(new SpringStandardDialect());
        springPCBuilder.setApplicationContextConfigLocation(null);

        executor.setProcessingContextBuilder(springPCBuilder);
        executor.setDialects(dialects);

        executor.execute("classpath:tests/getBoardSuccess.thtest");
        Assertions.assertTrue(executor.isAllOK());
    }
    @Test
    public void TestTestingFunctionality() {
        final List<IDialect> dialects = new ArrayList<IDialect>();
        final SpringWebProcessingContextBuilder springPCBuilder = new SpringWebProcessingContextBuilder();
        final TestExecutor executor = new TestExecutor();

        dialects.add(new SpringStandardDialect());
        springPCBuilder.setApplicationContextConfigLocation(null);

        executor.setProcessingContextBuilder(springPCBuilder);
        executor.setDialects(dialects);

        executor.execute("classpath:tests/HelloWorld.thtest");
        Assertions.assertTrue(executor.isAllOK());
    }
}
