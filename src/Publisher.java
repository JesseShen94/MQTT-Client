import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/***/
public class Publisher {
    private String USER_NAME = "students";
    private String PASSWORD = "33106331";
    private String clientID = "3310-u5890571";
    private String HOST = "tcp://comp3310.ddns.net:1883";
    private String $TOPIC = "studentreport/u5890571/";
    private int qos = 2;
    private MqttClient client;

    private String message;
    private String topic;
    /***/
    public Publisher() throws MqttException {
        client = new MqttClient(HOST, clientID, new MemoryPersistence());
        linkStart();
    }
    /***/
    private void linkStart(){
        MqttConnectOptions OPTION = new MqttConnectOptions();
        OPTION.setCleanSession(true);
        OPTION.setUserName(USER_NAME);
        OPTION.setPassword(PASSWORD.toCharArray());
        OPTION.setConnectionTimeout(10);
        OPTION.setKeepAliveInterval(20);
        client.setCallback(new CallBack());
        try {
            client.connect(OPTION);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
    /***/
    public void publish(String topic, String message) throws MqttException{
        MqttTopic MQTopic = client.getTopic($TOPIC+topic);
        MqttDeliveryToken courier = MQTopic.publish(message.getBytes(), qos, true);
        courier.waitForCompletion();
        System.out.println("Publishing on topic: "+ MQTopic.toString());
        System.out.println("Message: "+ message);
        System.out.println("Publish completely");
    }
    /***/
    public void disconnect() throws MqttException{
        client.disconnect();
        client.close();
    }


}
