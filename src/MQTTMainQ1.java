import org.eclipse.paho.client.mqttv3.MqttException;

public class MQTTMainQ1 {
    private static String T_slow_0 = "counter/slow/q0",
            T_slow_1 = "counter/slow/q1",
            T_slow_2 = "counter/slow/q2",
            T_fast_0 = "counter/fast/q0",
            T_fast_1 = "counter/fast/q1",
            T_fast_2 = "counter/fast/q2";
    public static void main(String[] args) throws MqttException {
        long startTime = System.currentTimeMillis();
        Client client = new Client(T_fast_2,2);
        client.start();
        try {
            Thread.sleep(1000*60);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        client.disconnect();
        long endTime = System.currentTimeMillis();
        System.out.println("Use time: "+ (endTime-startTime)/1000/60);

    }
}
