package overlord.myapplication.Utility;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * Created by OverlordPC on 22-Jan-16.
 */
public class Hash {
    public static String hash(String message, String  code){
        try{
            MessageDigest m = MessageDigest.getInstance(code);
            m.update(message.getBytes(), 0, message.length());
            return (new BigInteger(1, m.digest()).toString(16));
        }
        catch (java.security.NoSuchAlgorithmException n) {
        }
        return message;
    }
}
