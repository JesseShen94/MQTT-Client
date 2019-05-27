import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * Listener for testing Q1, 2, 3,
 *  ! NOT A PART OF ASSIGNMENT !
 *      ! DO NOT UPLOAD !
 * */
public class TestQ3 {
    public static void main(String[] args) throws InterruptedException, MqttException {
        Client client = new Client("studentreport/u5890571/#","3310-u0000000", 2);
        client.start();

        Thread.sleep(10000);

        client.disconnect();
    }

    //TODO: 打包啊woc, 好难搞;
    //TODO: remove any duplicated code, unused code.
    //TODO: increase the speed for OOO
    //TODO: check on linux machine
}
