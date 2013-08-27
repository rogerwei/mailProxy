/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package org.roger.study.ExClient.Security.SSL;

import sun.awt.im.InputMethodWindow;
import sun.security.provider.Sun;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Bogus {@link TrustManagerFactorySpi} which accepts any certificate
 * even if it is invalid.
 */
public class SecureTrustManagerFactory extends TrustManagerFactorySpi {

    private static final TrustManager DUMMY_TRUST_MANAGER = new X509TrustManager() {


        public X509Certificate[] getAcceptedIssuers() {

           return new X509Certificate[0];
        }

        public void checkClientTrusted(X509Certificate[] chain, String authType) {

            System.err.println( "UNKNOWN CLIENT CERTIFICATE: " + chain[0].getSubjectDN());

        /* X509TrustManager  sunJSSEX509TrustManager=null;
            try
            {
               TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
              KeyStore tks = KeyStore.getInstance("jks");
              tks.load(new FileInputStream("D:/ca/client_keystore"), "123456".toCharArray());
              tmf.init(tks);

              TrustManager tms [] = tmf.getTrustManagers();
                for (int i = 0; i < tms.length; i++) {
                    if (tms[i] instanceof X509TrustManager) {
                        sunJSSEX509TrustManager = (X509TrustManager) tms[i];
                       break;
                    }
                }

             sunJSSEX509TrustManager.checkClientTrusted(chain,authType);

            }catch (Exception e)
            {

            }       */

        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) {

          //  System.err.println("UNKNOWN SERVER CERTIFICATE: " + chain[0].getSubjectDN());
        }
    };



    public static TrustManager[] getTrustManagers() {
        return new TrustManager[] { DUMMY_TRUST_MANAGER };
    }

    @Override
    protected TrustManager[] engineGetTrustManagers() {
        return getTrustManagers();
    }

    @Override
    protected void engineInit(KeyStore keystore) throws KeyStoreException {
        // Unused
    }

    @Override
    protected void engineInit(ManagerFactoryParameters managerFactoryParameters)
            throws InvalidAlgorithmParameterException {
        // Unused
    }
}
