MQTTMainQ1.class : MQTTMainQ1.java
	javac -cp .:./org.eclipse.paho.client.mqttv3-1.2.1.jar -d  ./ ./Client.java ./MQTTMainQ1.java 
	javac -cp .:./org.eclipse.paho.client.mqttv3-1.2.1.jar -d  ./ ./Client.java ./Publisher.java ./CallBack.java ./MQTTMainQ2.java 

runQ1 : MQTTMainQ1.class
	java -cp .:./org.eclipse.paho.client.mqttv3-1.2.1.jar MQTTMainQ1

runQ2 : MQTTMainQ2.class
	java -cp .:./org.eclipse.paho.client.mqttv3-1.2.1.jar MQTTMainQ2

clean : 
	rm *.class