package abp.main;

import gov.nasa.jpf.vm.Verify;
import lock.LockPool;

public class DDuplicator<P,B> extends LockPool {
    private Channel<P> channel1;
    private Channel<B> channel2;
    private Cell<Boolean> finish;

    public DDuplicator(Channel<P> ch1,Channel<B> ch2,Cell<Boolean> f) {
        this.channel1 = ch1;
        this.channel2 = ch2;
        this.finish = f;
    }

    public void run() {
        while (true) {
//            try { Thread.sleep(100); }
//            catch (InterruptedException e) { }
//        	if (finish.get()) break;
            
            synchronized (this.getLock()) {
            	Verify.beginAtomic();
            	if (finish.get()) break;
            	P p = channel1.duptop();
            	Verify.endAtomic();
			}
            
            synchronized (this.getLock()) {
            	Verify.beginAtomic();
            	if (finish.get()) break;
            	B b = channel2.duptop();
            	Verify.endAtomic();
			}
            /*
            if (p != null)
                System.out.println("duplicated1: " + p);
            if (b != null)
                System.out.println("duplicated2: " + b);
            */
        }
    }
}