package com.example.idgenerator.Utils;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.SecureRandom;
import java.util.Enumeration;

@Component
public class GenerateNodeId {
    @Bean
    public Integer generatingNodeId() {
        int maxNodeVal = (int) Math.pow(2, 10);
        int nodeId;
        try {
            StringBuilder sb = new StringBuilder();
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
//                byte[] mac = networkInterface.getHardwareAddress();
                byte[] mac = networkInterface.getInetAddresses().nextElement().getAddress();
                if (mac != null) {
                    for (byte b : mac) {
                        sb.append(String.format("%02X", b));
                    }
                }
            }
            nodeId = sb.toString().hashCode();
        } catch (SocketException ex) {
            //in case of exception get a random number limited by max node size
            nodeId = (int) (new SecureRandom().nextInt() % Math.pow(2, 10));
        }
        nodeId = nodeId & maxNodeVal;
        return nodeId;
    }
}
