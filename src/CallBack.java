import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * This class implemented from MqttCallback.
 * This class is a modified Callback method from Mqtt lib
 * @author Yanlong LI, u5890571*/
public class CallBack implements MqttCallback {
    @Override
    public void connectionLost(Throwable throwable) {
        System.out.println("Connection lost");
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        System.out.println("Topic get: " + s);
        System.out.println("Qos get: "+ mqttMessage.getQos());
        System.out.println("Message get: "+ new String(mqttMessage.getPayload()));
    }
    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        System.out.println("Complete-------"+iMqttDeliveryToken.isComplete());
    }
}
