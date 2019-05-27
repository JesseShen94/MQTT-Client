import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.ArrayList;

/**
 * This class is the question 2 and 3
 * This class build based on Clint class
 * subscribe all counter with
 * QoS level 5 mins and publish the result to the broker
 *
 * @author Yanlong LI, u5890571
 * */
public class MQTTMainQ2 {
    private final static String[] TOP = {"counter/slow/q0","counter/slow/q1","counter/slow/q2","counter/fast/q0","counter/fast/q1","counter/fast/q2"};
    private final static String[] topics = {"slow/0/recv", "slow/0/loss", "slow/0/dupe", "slow/0/ooo", "slow/0/gap", "slow/0/gvar",
            "slow/1/recv", "slow/1/loss", "slow/1/dupe", "slow/1/ooo", "slow/1/gap", "slow/1/gvar",
            "slow/2/recv", "slow/2/loss", "slow/2/dupe", "slow/2/ooo", "slow/2/gap", "slow/2/gvar",
            "fast/0/recv", "fast/0/loss", "fast/0/dupe", "fast/0/ooo", "fast/0/gap", "fast/0/gvar",
            "fast/1/recv", "fast/1/loss", "fast/1/dupe", "fast/1/ooo", "fast/1/gap", "fast/1/gvar",
            "fast/2/recv", "fast/2/loss", "fast/2/dupe", "fast/2/ooo", "fast/2/gap", "fast/2/gvar"};
    private static final long FIVE_MIN = 1000*60*5;


    public static void main(String[] args) throws MqttException {
        long startTime = System.currentTimeMillis();
        ArrayList<ArrayList<String>> DataStorage = new ArrayList<>();
        for (int i = 0; i < TOP.length; i++) {
            Client client = new Client(TOP[i], i%3);
            client.start();
            try {
                Thread.sleep(FIVE_MIN);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            DataStorage.add(client.disconnect(FIVE_MIN,true));
        }

//        Client client = new Client(T_slow_0, 0);
//        client.start();
//        try {
//            Thread.sleep(1000*60*5);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        client.disconnect(1000*60*5);
        long endTime = System.currentTimeMillis();
        System.out.println("Use time: "+ (endTime-startTime)/1000/60);
        Publisher publisher = new Publisher(2, true);
        publisher.publish("language","Java, used package: org.eclipse.paho.client.mqttv3-1.2.1.jar");
        publisher.publish("network ", "WAN: NBN. LAN: Wi-Fi");
        int TOPIC_index = 0;
        for(ArrayList<String> arr : DataStorage){
            for(String data : arr){
                System.out.println(topics[TOPIC_index] + " " +data);
                publisher.publish(topics[TOPIC_index], data);
                TOPIC_index++;
            }
        }
        publisher.disconnect();
    }
}
