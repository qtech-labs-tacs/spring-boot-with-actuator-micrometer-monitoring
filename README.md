### Spring Actuator, DevTools, Micrometer, Prometheus, Grafana


Spring Boot web application can be monitored using Micrometer which exposes metrics from our application, Prometheus which stores the metric data, and Grafana to visualize the data in graphs.


* Each individual endpoint can be enabled or disabled and exposed (made remotely accessible) over HTTP or JMX. 

* An endpoint is considered to be available when it is both  __enabled__  and  __exposed__

* By default, all endpoints except for shutdown are enabled. 

* Since Endpoints may contain sensitive information, careful consideration should be given about when to expose them.<br>
<br>

##### Few important actuator endpoints are listed below

```bash
 http://<host>:<port>/actuator/
 http://<host>:<port>/actuator/health
 http://<host>:<port>/actuator/configprops
 http://<host>:<port>/actuator/metrics/
 http://<host>:<port>/actuator/env/
 http://<host>:<port>/actuator/prometheus 
 http://<host>:<port>/actuator/loggers
 http://<host>:<port>/actuator/heapdump
 http://<host>:<port>/actuator/threaddump
```

### Micrometer

* Application metrics recorded by Micrometer are intended to be used to observe, alert, and react to the current/recent operational state of your environment.


### Prometheus

* An in-memory dimensional time series database with a simple built-in UI, a custom query language, and math operations. 
* Prometheus is designed to operate on a pull model, scraping metrics from application instances periodically based on service discovery.

```bash
docker run -d --name=prometheus -p 9090:9090 -v <PATH_TO_prometheus.yml_FILE>:/etc/prometheus/prometheus.yml prom/prometheus --config.file=/etc/prometheus/prometheus.yml
```


#### Grafana

* Grafana is open source visualization and analytics software. 
* It allows you to query, visualize, alert on, and explore your metrics no matter where they are stored. 
* In plain English, it provides you with tools to turn your time-series database (TSDB) data into beautiful graphs and visualizations.


```bash
docker run -d -p 3000:3000 grafana/grafana
```


#### Heap Dump

* A heap dump is a snapshot of the memory of a Java process at a certain point of time.
* The snapshot contains information about the java objects and classes in the heap at the moment the snapshot was triggered. 



#### Thread Dump

* A thread dump is a snapshot of the state of all threads that are part of the process. 
* The state of each thread is presented in a stack trace, which shows the contents of a thread’s stack. 
* This is especially useful if application seems stuck, or is running out of resources, a thread dump will reveal the state of the server.


#### How to create heap dump and thread dump

* Step 1: Find the PID of the java process

```bash
Java JDK ships with the jps command which lists all java process ids running on the machine including the PID of the process.
COMMAND: jps -l -m -v 
```


* Step 2: Request a Thread Dump from the JVM

```bash
The jstack tool prints thread dumps to the command line console or a file, 
The thread dumps can be used to see which threads are blocked 
The -l argument provides additional "Locked ownable synchronizers" for each thread 
COMMAND: jstack -l <pid-from-step-1> >> microservice-name.tdump_<pid-from-step-1>_<timestamp>
```


* Step 3: Request a Heap Dump from the JVM

```bash
The jmap tool it prints out what is in the memory to see what changed to the command line console or a file.
To obtain a heap dump using jmap, use the PID of the java process to run the following commands :

With -heap to see the java heap summary:
COMMAND: dk\bin\jmap -heap <PID> >> <microservice-name>.jmap_<PID>_<timestamp>

With -dump to dump the full java heap:
COMMAND: map -dump:file=<filename> <pid> 
```


* NOTE: We can also take heap and thread dumps using spring actuator prod-ready endpoints 
    * http://<host>:<port>/actuator/heapdump 
    * http://<host>:<port>/actuator/threaddump


* NOTE:
     * CPU utilization high    - Thread dump and analyses
     * Memory utilization      - Heap Dump and analyses


#### Online Tools to analyse Heap and Thread dump

* [Thread dump analysis](https://fastthread.io/)
* [GC analysis](https://www.gceasy.io/)
* [Heap Dump](https://heaphero.io/)

#### Eclipse Tools

* **Memory Analyzer (MAT)**
     * The Eclipse Memory Analyzer is a fast and feature-rich Java heap analyzer that helps you find memory leaks and reduce memory consumption.
     * Install Eclipse MAT from eclipse market place


#### JDK Tools

* **VisualVM**: 
     * VisualVM is a visual tool integrating commandline JDK tools and lightweight profiling capabilities. Designed for both development and production time use. 
     * In comes bundled with JDK.



#### OpenFile Count and Analysis


* lsof | wc -l


#### Useful linux command for troubleshooting

* **Memory Management Commands**<br>
<br>
* display system memory in mbs

		COMMAND: free -m

* Display system memory in gbs
     
     	COMMAND: free -h    
     		
* press 1 to check various cores
     
       	COMMAND: top      
       		
* to check memory and cpu utilization
     
       COMMAND: htop
     
* report file system disk space usage
 
     COMMAND: df -h
 
 * lays out the memory usage statistics
 
    COMMAND: vmstat -s 


* **System OS information**<br>
<br>
    
* Print distribution information

    lsb_release -a



* **Top 10 Memory Consuming Process**

```bash     
ps -auxf | sort -nr -k 4 | head -10
```


* **Top 10 CPU Consuming Process**
      
```bash
ps -auxf | sort -nr -k 3 | head -10
```

#### How to change the level of the particular logger without stopping our application, on the fly

* To check all loggers --> 

	http://localhost:8080/actuator/loggers/

* To check logging level of a particular logger

    http://localhost:8080/actuator/loggers/com.yagnaiq
	
	output:
	{
	  "configuredLevel":null,
	 "effectiveLevel":"INFO"
	 }

* To change logging level to __debug__ we need to send a POST request to the same logger URL.

	POST http://localhost:8080/actuator/loggers/com.yagnaiq
	content-type: application/json
	{
	  "configuredLevel": "DEBUG"
	}
	
	Now check the output of logging level with  above actuator url (http://localhost:8080/actuator/loggers/com.yagnaiq)
	
	output:
	{
	  "configuredLevel":"DEBUG",
	  "effectiveLevel":"DEBUG"
	}
	
	
	
  
  



#### Important Links

* Prometheus offical docs

```bash
https://prometheus.io/docs/introduction/overview/
```

* Grafana

```bash
https://grafana.com/docs/grafana/latest/getting-started/
```

* Spring Actuator offical docs

```bash
https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-features.html
```

* Micrometer official docs

```bash
https://micrometer.io/docs
```
