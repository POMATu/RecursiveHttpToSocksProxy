package com.uncensor;

import com.meituan.tools.proxy.JavaHttpProxy;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.ArrayList;

public class Main {

    public static String[] fuckers;

    public static ArrayList<Socks> socks = new ArrayList<>();
    private static String bind = "0.0.0.0";
    private static String port;

    public static int timeout = 3000; // sorta fair timeout not to wait too long

    public static void main(String[] args) {

        try {

            timeout = Integer.parseInt(args[0]);

            if (!args[1].contains(":")) {
                port = args[1];
            } else {
                bind = args[1].split(":")[0];
                port = args[1].split(":")[1];
            }

            if (!args[2].contains(":")) { // big brother locations
                fuckers = new String[]{args[2]};
            } else {
                fuckers = args[2].split(":");
            }

            if (args[3].contains(":")) {
                Authenticator.setDefault(getAuth(args[3].split(":")[0], args[3].split(":")[1]));
            }

            // I am using direct route first because http torrent trackers (if not censored) shall get my real ip otherwise I cant seed
            // due to my fucked routing setups I am using direct route thru socks as well, if you aint need no socks for direct access you shall patch it and make pull request


            for (int i = 4; i < args.length; i++) {
                socks.add(new Socks(
                        args[i].split(":")[0],
                        Integer.parseInt(args[i].split(":")[1])
                ));
            }
            if (socks.isEmpty())
                throw new Exception("help");

            args = new String[4];
            args[0] = bind;
            args[1] = port;
            args[2] = "/tmp/files"; // if you know chinese please tell me what is it for cuz i have no idea
            args[3] = "running"; // something about gzip unwrapping well fuck knows it just works so i dont give a fuck
            try {
                JavaHttpProxy.main(args);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.out.println("RecursiveHttpToSocksProxy\n" +
                    "\n" +
                    "This http proxy acts as regular http proxy with socks upstreams, but also analyzes the reposnse location header.\n" +
                    "If it sees big brother redirection in location header - it redoes the request thru next upstream socks.\n" +
                    "Socks auth supported.\n" +
                    "\n" +
                    "Usage with auth:\n" +
                    "java -jar RecursiveHttpToSocksProxy.jar timeoutmsecs [bindip:]bindport lawfilter.ertelecom[:info.rt.ru[:info.ertelecom]] proxyusername:proxypassword socks1:port1 socks2:port2 socks3:port3 ...\n" +
                    "\n" +
                    "Usage without auth:\n" +
                    "java -jar RecursiveHttpToSocksProxy.jar timeoutmsecs [bindip:]bindport lawfilter.ertelecom[:info.rt.ru[:info.ertelecom]] null socks1:port1 socks2:port2 socks3:port3 ...\n");
        }
    }

    public static Authenticator getAuth(String user, String password) {
        return new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return (new PasswordAuthentication(user, password.toCharArray()));
            }
        };
    }
}
