import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Pattern;

/**
 * This class is the client class for process mqtt request.
 *
 * @author Yanlong LI, u5890571
 * */
public class Client {
    private ArrayList<String> $MessageStream = new ArrayList<>();
    private HashSet<String> $DuplicateMessage = new HashSet<>();
    private ArrayList<Long> $TimeGap = new ArrayList<>();
    private String USER_NAME = "students";
    private String PASSWORD = "33106331";
    private String clientID = "3310-u5890571";
    private String HOST = "tcp://comp3310.ddns.net:1883";
    private String TOPIC = "$SYS";
    private int qos = 0;
    private boolean PRINT = true;
    private Pattern isDig = Pattern.compile("[0-9]*");
    private HashSet<String> TOPICS = new HashSet<>();
    private MqttClient client;
    private MqttConnectOptions OPTION;
    private int MQTTQOS = -1;

    /**
     * The default constructor without parameter.
     * */
    public Client(){/* Keep default*/}

    /**
     * The constructor for new MQTT client.
     * All client information needed
     * */
    public Client(String UserName, String Password, String ClientID, String host, String topic, int Qos){
        this.USER_NAME = UserName;
        this.PASSWORD = Password;
        this.clientID = ClientID;
        this.HOST = host;
        this.TOPIC = topic;
        this.qos = Qos;
    }

    /**
     * The constructor which set up topic only.
     * */
    public Client(String topic){
        this.TOPIC = topic;
    }

    /**
     * The constructor set up topic and QoS.
     * */
    public Client(String topic, int Qos){
        this.TOPIC = topic;
        this.qos = Qos;
    }

    /**
     * The constructor set up topic, QoS and determined print messages
     * on display or not.
     * */
    public Client(String topic, int Qos, boolean print){
        this.TOPIC = topic;
        this.qos = Qos;
        this.PRINT = print;
    }

    /**
     * Establish connection and receive messages from broker.
     * */
    public void start(){
        try{
            client = new MqttClient(HOST, clientID, new MemoryPersistence());
            OPTION = new MqttConnectOptions();
            OPTION.setUserName(USER_NAME);
            OPTION.setPassword(PASSWORD.toCharArray());
            OPTION.setCleanSession(true);
            OPTION.setConnectionTimeout(10);
            OPTION.setKeepAliveInterval(20);

            client.setCallback(new MqttCallback() {
                /**
                 * Closure Callback class.
                 * Handle the message from broker
                 * and collect the massage for statistic*/
                private long currentTime = System.currentTimeMillis();
                @Override
                public void connectionLost(Throwable throwable) {
                    System.out.println("Connection lost");
                }

                @Override
                public void messageArrived(String s, MqttMessage mqttMessage) {
                    long TimeGet = System.currentTimeMillis();
                    $TimeGap.add(TimeGet- currentTime);
                    currentTime = TimeGet;
                    TOPICS.add(s);
                    if(PRINT){
                        System.out.println("Topic get: " + s);
                        System.out.println("Qos get: "+ mqttMessage.getQos());
                        System.out.println("Message get: "+ new String(mqttMessage.getPayload()));
                    }
                    MQTTQOS = mqttMessage.getQos();
                    // Handel the message only with numbers
                    if(isDig.matcher(new String(mqttMessage.getPayload())).matches()){
                        $MessageStream.add(new String(mqttMessage.getPayload()));
                        $DuplicateMessage.add(new String(mqttMessage.getPayload()));
                    }

                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                    System.out.println("Complete-------"+iMqttDeliveryToken.isComplete());

                }
            });
            client.connect(OPTION);
            client.subscribe(TOPIC, qos);
        }catch (Exception e){}
    }

    /**
     * The private method to analysis the data.
     * Analysis include the lost rate, duplicate rate, receive rate, average time gap between all message,
     * mis-ordered message rate and the variation about time gap between all message.
     *
     * This method handle all calculation via BigDecimal to avoid out of bound in int.
     *
     * @param MessageStream a list contain all message get from broker.
     * @param TimeGap a list contain all time gap between each message.
     * @param DuplicateMessage a set contain all non-duplicate message.
     * @param duration a double data about how this session take.
     * */
    private void statistic(ArrayList<String> MessageStream, ArrayList<Long> TimeGap, HashSet<String> DuplicateMessage, long duration){
        double DUPL_RATE = MessageStream.size()==0?0:DuplicateMessage.size()/(double)MessageStream.size();
        BigDecimal TotalLength = new BigDecimal("0");
        BigDecimal OOO = new BigDecimal("0");

        for(String message : MessageStream){
            ArrayList<Boolean> out = new ArrayList<>();
            if(MessageStream.indexOf(message) <= 10){
                for (int i = 0; i < MessageStream.indexOf(message); i++) {
                    if(new BigDecimal(MessageStream.get(i)).compareTo(new BigDecimal(message)) == 1) out.add(true);// priv greater than current
                }
            }else{
                for (int i = MessageStream.indexOf(message) - 10; i < MessageStream.indexOf(message); i++) {
                    if(new BigDecimal(MessageStream.get(i)).compareTo(new BigDecimal(message)) == 1) out.add(true);// priv greater than current
                }
            }
            if(out.contains(true)) OOO = OOO.add(new BigDecimal(1));
        }
        BigDecimal OOO_RATE = OOO.divide(new BigDecimal(MessageStream.size()), 4, BigDecimal.ROUND_HALF_UP);
        OOO_RATE = OOO_RATE.multiply(new BigDecimal(100));
        OOO_RATE = OOO_RATE.divide(new BigDecimal(1), 2, BigDecimal.ROUND_HALF_UP);
        //Out of order
        BigDecimal minDec = new BigDecimal(MessageStream.get(0));
        BigDecimal maxDec = new BigDecimal(MessageStream.get(MessageStream.size()-1));
        TotalLength = maxDec.subtract(minDec).add(new BigDecimal(1));
        BigDecimal LOST_Gap = new BigDecimal(MessageStream.size()+"");
        //MAX - MIN + 1 = actual message should have
        //MessageStream.size() = actual received.
        //MessageStream.size()/(MAX - MIN + 1) = LOST_RATE
        BigDecimal LOST_RATE = LOST_Gap.divide((TotalLength), 4, BigDecimal.ROUND_HALF_UP);
        LOST_RATE = (new BigDecimal(1)).subtract(LOST_RATE);
        LOST_RATE = LOST_RATE.multiply(new BigDecimal(100));
        LOST_RATE = LOST_RATE.divide(new BigDecimal(1), 2, BigDecimal.ROUND_HALF_UP);

        BigDecimal REV_RATE = (new BigDecimal(MessageStream.size())).divide(new BigDecimal(duration/1000), 2,BigDecimal.ROUND_UP);
        //MessageStream.size() / total time = RECEIVE RATE

        BigDecimal Timetotal = new BigDecimal("0");
        BigDecimal TimeVaria = new BigDecimal("0");
        for(long time : TimeGap){
            Timetotal = Timetotal.add(new BigDecimal(time));
        }
        Timetotal = Timetotal.divide(new BigDecimal(TimeGap.size()), 2, BigDecimal.ROUND_HALF_UP);
        // Mean for each inner message gap

        for(long time : TimeGap){
            BigDecimal part = (new BigDecimal(time)).subtract(Timetotal);
            part = part.multiply(part);
            TimeVaria = TimeVaria.add(part);
        }
        TimeVaria = TimeVaria.divide(new BigDecimal(TimeGap.size()), 2, BigDecimal.ROUND_HALF_UP);
        System.out.print("Topics: ");
        for(String topic : TOPICS){
            System.out.print(topic + "; ");
        }
        System.out.println();
        System.out.println("QoS: "+ MQTTQOS);
        System.out.println("Total length actual receive: " + MessageStream.size());
        System.out.println("Total length should receive: " + TotalLength.toString());
        System.out.println("Duplicate rate: " + (100 - (DUPL_RATE*100))+"%");
        System.out.println("Lost rate: " + LOST_RATE.toString()+"%");
        System.out.println("Receive rate: " + REV_RATE.toString()+" messages pre sec");
        System.out.println("Arv time: " + Timetotal.toString() + " mils");
        System.out.println("Variation:" + TimeVaria.toString());
        System.out.println("Out of order: " + OOO.toString());
        System.out.println("Out of order: " + OOO_RATE.toString()+"%");
    }

    /**
     * Terminate the connection between the client and the broker.
     * */
    public void disconnect() throws MqttException {
        client.disconnect();
        client.close();
    }

    /**
     * Terminate the connection between the client and the broker.
     *
     * @param duration a time about how long this session token.*/
    public void disconnect(long duration) throws MqttException {
        client.disconnect();
        client.close();
        statistic($MessageStream, $TimeGap, $DuplicateMessage, duration);
    }
}
