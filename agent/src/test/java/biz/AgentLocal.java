package biz;

public class AgentLocal {

    private void fun1() throws Exception {
        System.out.println("this is fun 1.");
        Thread.sleep(500);
    }

    private void fun2() throws Exception {
        System.out.println("this is fun 2.");
        Thread.sleep(500);
    }

    // add VM options: -javaagent:D:\Users\Desktop\fd\agent-demo\agent\target=first args 333
    public static void main(String[] args) throws Exception {
        System.out.println(System.getProperty("user.dir"));
        AgentLocal test = new AgentLocal();
        test.fun1();
        test.fun2();

        InstrumentLocal instrumentLocal = new InstrumentLocal();
        instrumentLocal.fun3();
        instrumentLocal.fun4();


        InstrumentLocal instrumentLocal2 = new InstrumentLocal();
        instrumentLocal2.fun3();
        instrumentLocal2.fun4();
    }
}
