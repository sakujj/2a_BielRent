package test.fpmibsu.bielrent.listeners;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestPlan;
import test.fpmibsu.bielrent.connectionpool.TestConnectionPoolImpl;

public class CloseTestConnectionPoolListener implements TestExecutionListener {
    @Override
    public void testPlanExecutionFinished(TestPlan testPlan) {
        TestConnectionPoolImpl.getInstance().close();
        System.out.println("test connection pool is now closed");
    }
}
