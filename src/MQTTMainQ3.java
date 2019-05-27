import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * This class is the main class for Q3.
 *
 * This class based on the Publisher class
 * and publish all statistical data to broker.
 *
 * Cooperate with MQTTMainQ2. All data copied
 * for MQTTMainQ2
 *
 * @author Yanlong LI, u5890571
 * */
public class MQTTMainQ3 {
    private static String[] topics = {"slow/0/recv", "slow/0/loss", "slow/0/dupe", "slow/0/ooo", "slow/0/gap", "slow/0/gvar",
            "slow/1/recv", "slow/1/loss", "slow/1/dupe", "slow/1/ooo", "slow/1/gap", "slow/1/gvar",
            "slow/2/recv", "slow/2/loss", "slow/2/dupe", "slow/2/ooo", "slow/2/gap", "slow/2/gvar",
            "fast/0/recv", "fast/0/loss", "fast/0/dupe", "fast/0/ooo", "fast/0/gap", "fast/0/gvar",
            "fast/1/recv", "fast/1/loss", "fast/1/dupe", "fast/1/ooo", "fast/1/gap", "fast/1/gvar",
            "fast/2/recv", "fast/2/loss", "fast/2/dupe", "fast/2/ooo", "fast/2/gap", "fast/2/gvar"};
    private static String[] BCKUPmessages = {"0.98","0","0","0","1028.24","88.30",
            "0.98","0","0","0","1028.16","52.65",
            "0.98","0","0","0","1029.9","66.51",
            "272","0","0","0","3.69","21.76",
            "23.34","88.47","0","0","43.02","103.83",
            "11.86","93.98","0","0","84.66","182.62"};

    public static void main(String[] args) throws MqttException {
        Publisher publisher = new Publisher();
        publisher.publish("language","Java, used package: org.eclipse.paho.client.mqttv3-1.2.1.jar");
        publisher.publish("network ", "WAN: NBN. LAN: Wi-Fi");
        for (int i = 0; i < 36; i++) {
            publisher.publish(topics[i], BCKUPmessages[i]);
        }
        publisher.disconnect();
    }
}
