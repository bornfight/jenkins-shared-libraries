package com.bornfight.test_step;

import com.bornfight.context.ContextRegistry;
import com.bornfight.context.IContext;
import com.bornfight.IStepExecutor;
import com.bornfight.step.TestStep;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TestStepTest {
    private IContext context;
    private IStepExecutor steps;

    @Before
    public void setup() {
        context = mock(IContext.class);
        steps = mock(IStepExecutor.class);

        when(context.getStepExecutor()).thenReturn(steps);

        ContextRegistry.registerContext(context);
    }

    @Test
    public void testShStep() {
        String message = "Pozdrav iz mocka";
        TestStep build = new TestStep(message);

        build.execute();

        verify(steps).sh("echo \"" + message + "\"");
    }
}
