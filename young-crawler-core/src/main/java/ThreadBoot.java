/**
 * Created by young.yang on 2016/8/31.
 */
public class ThreadBoot {
    public static void main(String[] args) throws InterruptedException {
       Thread thread1 = new Thread1();
        Runnable runnable = new Runnable1();
        Thread thread2 = new Thread(runnable);
        thread1.start();
        thread2.start();
        Thread.sleep(5000);
        thread1.start();
        new Thread(runnable).start();
    }
}
