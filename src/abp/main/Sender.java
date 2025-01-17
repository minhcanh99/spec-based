package abp.main;
import java.util.Collection;
import java.util.List;

import config.CaseStudy;
import gov.nasa.jpf.vm.Verify;
import lock.LockPool;

public class Sender<P> extends LockPool {
    private Channel<Pair<P,Boolean>> channel1;
    private Channel<Boolean> channel2;
    private Collection<P> packetsToBeSent;
    private Cell<Boolean> finish;
    private Boolean flag1;	// initially `true`
    private int index;

    public Sender(Channel<Pair<P,Boolean>> ch1,
                  Channel<Boolean> ch2,
                  Collection<P> c,
                  Cell<Boolean> f,
                  Boolean flag1,
                  int index) {
        this.channel1 = ch1;
        this.channel2 = ch2;
        this.packetsToBeSent = c;
        this.finish = f;
        this.flag1 = flag1;
        this.index = index;
    }

    public void run() {
    	for (int i = index; i < packetsToBeSent.size(); i ++) {
    		Pair<P,Boolean> pr = new Pair<P,Boolean>(((List<P>)packetsToBeSent).get(i),flag1);
    		
            while (true) {
            	synchronized (this.getLock()) {
            		Verify.beginAtomic();
                    channel1.put(pr);
                    Verify.endAtomic();
				}
                /*
                if (p != null)
                    System.out.println("Snd-Sending " + p);
                */
                
                Boolean flag = false;
                synchronized (this.getLock()) {
	                Verify.beginAtomic();
	                Boolean b = channel2.get();
	                if (b != null) {
	                    if (b == !flag1) {
	                        /*
	                        System.out.println("Snd-Received " + b);
	                        */
	                    	
	                        flag1 = !flag1;
	                        index ++;
	                        flag = true;
	                    }
	                }
	                Verify.endAtomic();
                }
                
                if (flag) {
                	break;
                }
            }
    	}
        finish.set(true);
        /*
        System.out.println("Sender has sent all packets!");
        */
    }
}