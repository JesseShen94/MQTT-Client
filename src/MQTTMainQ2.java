import org.eclipse.paho.client.mqttv3.MqttException;

/***/
public class MQTTMainQ2 {
    private static String T_slow_0 = "counter/slow/q0",
            T_slow_1 = "counter/slow/q1",
            T_slow_2 = "counter/slow/q2",
            T_fast_0 = "counter/fast/q0",
            T_fast_1 = "counter/fast/q1",
            T_fast_2 = "counter/fast/q2",
            T_fast = "counter/fast/#",
            T_slow = "counter/slow/#";
    private static String[] topics = {"language", "network ", "slow/0/recv", "slow/0/loss", "slow/0/ooo", "slow/0/gap", "slow/0/gvar",
            "slow/1/recv", "slow/1/loss", "slow/1/ooo", "slow/1/gap", "slow/1/gvar",
            "slow/2/recv", "slow/2/loss", "slow/2/ooo", "slow/2/gap", "slow/2/gvar",
            "fast/0/recv", "fast/0/loss", "fast/0/ooo", "fast/0/gap", "fast/0/gvar",
            "fast/1/recv", "fast/1/loss", "fast/1/ooo", "fast/1/gap", "fast/1/gvar",
            "fast/2/recv", "fast/2/loss", "fast/2/ooo", "fast/2/gap", "fast/2/gvar"};


    public static void main(String[] args) throws MqttException {
        long startTime = System.currentTimeMillis();
        Client client = new Client("$SYS/#", 0);
        client.start();
        try {
            Thread.sleep(1000*60*5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        client.disconnect(1000*60*5);
        long endTime = System.currentTimeMillis();
        System.out.println("Use time: "+ (endTime-startTime)/1000/60);
    }
}
