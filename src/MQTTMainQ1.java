import org.eclipse.paho.client.mqttv3.MqttException;

/***/
public class MQTTMainQ1 {
    private static String[] T_slow = {"counter/slow/q0","counter/slow/q1","counter/slow/q2"};
    public static void main(String[] args) throws MqttException {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 3; i++) {
            Client client = new Client(T_slow[i], i);
            client.start();
            try {
                Thread.sleep(1000*6);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            client.disconnect(1000*6);
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Use time: "+ (endTime-startTime)/1000/60);
    }
}
