import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * The main class for the question 1
 * this class will subscribe slow counter
 * channel on 3 different level QoS and print
 * the report for each level.
 *
 * Using the Client class.
 *
 * @author Yanlong LI, u5890571
 * */
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
