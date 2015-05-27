package org.bartelby.stat;

import org.apache.commons.collections4.queue.CircularFifoQueue;

public class BartelbySniffer {

	static protected long currentProcess = 0;
	
	public BartelbySniffer() {
	}

	static public void addProcess(){
		BartelbySniffer.currentProcess++;
	}
	
	static public void rmProcess(){
		BartelbySniffer.currentProcess--;
	}
}
