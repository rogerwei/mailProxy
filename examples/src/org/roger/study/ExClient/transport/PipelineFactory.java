package org.roger.study.ExClient.transport;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.handler.codec.http.HttpClientCodec;
import org.jboss.netty.handler.codec.http.HttpContentDecompressor;
import org.jboss.netty.handler.ssl.SslHandler;
import org.roger.study.ExClient.Security.SSL.SecureSslContextFactory;
import org.roger.study.ExClient.configuration.Configs;

import javax.net.ssl.SSLEngine;

import static org.jboss.netty.channel.Channels.pipeline;

/**
 * Created with IntelliJ IDEA.
 * User: next
 * Date: 13-8-21
 * Time: 上午11:07
 * To change this template use File | Settings | File Templates.
 */
public class PipelineFactory implements ChannelPipelineFactory {
    @Override
    public ChannelPipeline getPipeline() throws Exception {
        ChannelPipeline pipeline = pipeline();

        if (Configs.isSslEnabled()) {
            SecureSslContextFactory ss =  new    SecureSslContextFactory();
            SSLEngine engine = ss.getClientContext().createSSLEngine();
            engine.setUseClientMode(true);
            pipeline.addLast("ssl", new SslHandler(engine));
        }

        pipeline.addLast("codec", new HttpClientCodec());
        pipeline.addLast("inflater", new HttpContentDecompressor());
        pipeline.addLast("handler", new ClientHandler());
        return pipeline;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
