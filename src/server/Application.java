package server;

import config.CaseStudy;
import jpf.common.HeapJPF;
import server.factory.ServerFactory;
import server.instances.RabbitMQ;
import server.instances.Redis;

/**
 * Application that contains all important instances
 * 
 * @author OgataLab
 *
 */
public class Application {

	CaseStudy cs = null;
	HeapJPF heap = null;
	ServerFactory serverFactory = null;
	Redis redis = null;
	RabbitMQ rabbitMQ = null;

	public Application(ServerFactory serverFactory, CaseStudy cs) {
		this.serverFactory = serverFactory;
		this.cs = cs;
		this.createServer();
	}

	private void createServer() {
		this.redis = this.serverFactory.createRedis();
		this.rabbitMQ = this.serverFactory.createRabbitMQ();
	}
	
	/**
	 * Get ServerFactory configuration
	 * 
	 * @return {@link ServerFactory}
	 */
	public ServerFactory getServerFactory() {
		return serverFactory;
	}

	/**
	 * Get Redis configuration
	 * @return
	 */
	public Redis getRedis() {
		return redis;
	}

	/**
	 * Get RabbitMQ configuration
	 * 
	 * @return {@link RabbitMQ}
	 */
	public RabbitMQ getRabbitMQ() {
		return rabbitMQ;
	}

	/**
	 * Get CaseStudy that is used
	 * 
	 * @return {@link CaseStudy}
	 */
	public CaseStudy getCaseStudy() {
		return cs;
	}
	
	/**
	 * Setup heapJPF
	 * 
	 * @param heap
	 */
	public void setHeapJPF(HeapJPF heap) {
		this.heap = heap;
	}
	
	public HeapJPF getHeapJPF() {
		return this.heap;
	}

}
