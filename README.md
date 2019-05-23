# MQTT-Client
The MQTT for COMP3310/6331
impled of ooo and the dup rate:
use the current one to minus last one, if equal then dup, if smaller than 0 then ooo, else fine.\n
NOTHING CHANGE ALOT.\n
  
BigInteger b = new BigInteger(message);\n
for(String message : Array){\n
    BigInteger bi = new BigInteger(message);\n
    if(bi.sub(b) < 0) ooo++;\n
    else if(bi.sub(b) == 0) dup++;\n
}\n
