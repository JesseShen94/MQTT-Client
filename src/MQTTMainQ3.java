import org.eclipse.paho.client.mqttv3.MqttException;

public class MQTTMainQ3 {
    public static void main(String[] args) throws MqttException {
        Publisher publisher = new Publisher();
        publisher.publish("language","Java");
    }
}
