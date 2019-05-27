import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * Listener for testing Q1, 2, 3,
 *  ! NOT A PART OF ASSIGNMENT !
 *      ! DO NOT UPLOAD !
 * */
public class TestQ3 {
    public static void main(String[] args) throws InterruptedException, MqttException {
        Client client = new Client("studentreport/u5890571/#", 0);
        client.start();
    }

    //TODO 打包啊woc, 好难搞
}
