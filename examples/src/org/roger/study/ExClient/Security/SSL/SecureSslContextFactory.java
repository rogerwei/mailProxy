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
import org.jboss.netty.handler.ssl.SslHandler;
import org.roger.study.ExClient.configuration.Configs;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.Security;

/**
 * Creates a bogus {@link SSLContext}.  A client-side context created by this
 * factory accepts any certificate even if it is invalid.  A server-side context
 * created by this factory sends a bogus certificate defined in {@link SecureChatKeyStore}.
 * <p>
 * You will have to create your context differently in a real world application.
 *
 * <h3>Client Certificate Authentication</h3>
 *
 * To enable client certificate authentication:
 * <ul>
 * <li>Enable client authentication on the server side by calling
 *     {@link SSLEngine#setNeedClientAuth(boolean)} before creating
 *     {@link SslHandler}.</li>
 * <li>When initializing an {@link SSLContext} on the client side,
 *     specify the {@link KeyManager} that contains the client certificate as
 *     the first argument of {@link SSLContext#init(KeyManager[], TrustManager[], SecureRandom)}.</li>
 * <li>When initializing an {@link SSLContext} on the server side,
 *     specify the proper {@link TrustManager} as the second argument of
 *     {@link SSLContext#init(KeyManager[], TrustManager[], SecureRandom)}
 *     to validate the client certificate.</li>
 * </ul>
 */

public  class SecureSslContextFactory {

    //private static final Logger LOGGER = Logger.getLogger(SecureSslContextFactory.class);
    private static final String PROTOCOL = "SSL";
    private static  SSLContext CLIENT_CONTEXT;
    private  static String  algorithm ;

    static {
        algorithm = "SunX509";
    }

     static
    {
        SSLContext clientContext;

        try {
            KeyStore pfx = KeyStore.getInstance("pkcs12");
            char[] password = Configs.getPfxPassword().toCharArray();
            pfx.load(new FileInputStream(Configs.getPfx()),password);
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(algorithm);
            kmf.init(pfx, password);

            TrustManagerFactory tmf = TrustManagerFactory.getInstance(algorithm);
            KeyStore ca = KeyStore.getInstance("jks");
            ca.load(new FileInputStream(Configs.getCa()), Configs.getCaPassword().toCharArray());
            tmf.init(ca);

            clientContext = SSLContext.getInstance(PROTOCOL);
            clientContext.init(kmf.getKeyManagers() ,SecureTrustManagerFactory.getTrustManagers(), /*tmf.getTrustManagers(),*/ null);

        } catch (Exception e) {
            throw new Error( "Failed to initialize the server-side SSLContext", e);
        }



        CLIENT_CONTEXT = clientContext;
    }



    public static SSLContext getClientContext() {
        return CLIENT_CONTEXT;
    }

    public SecureSslContextFactory() {
    }
}
