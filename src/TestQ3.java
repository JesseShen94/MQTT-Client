import org.eclipse.paho.client.mqttv3.MqttException;

public class TestQ3 {
    public static void main(String[] args) throws InterruptedException, MqttException {
        Client client = new Client("studentreport/u5890571/language", 2);
        client.start();
        Thread.sleep(1000);
        client.disconnect();
    }
}
