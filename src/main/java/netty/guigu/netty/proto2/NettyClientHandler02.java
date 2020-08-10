package netty.guigu.netty.proto2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import netty.guigu.netty.proto.StudentProto;

import java.time.LocalDateTime;
import java.util.Random;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-08 22:02
 */
public class NettyClientHandler02 extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client:" + ctx);
        int random = new Random().nextInt(30);
        MyDataInfo.MyMessage myMessage = null;
        myMessage = MyDataInfo.MyMessage.newBuilder().setDataType(MyDataInfo.MyMessage.DataType.WorkerType)
                .setWorker(MyDataInfo.Worker.newBuilder()
                        .setAge(5).setName("刘德华").build()).build();
//        if (0 != random) {//发送student对象
//            myMessage = MyDataInfo.MyMessage.newBuilder().setDataType(MyDataInfo.MyMessage.DataType.StudentType)
//                    .setStudent(MyDataInfo.Student.newBuilder()
//                            .setId(5).setName("胡冉").build()).build();
//
//        } else {
//            myMessage = MyDataInfo.MyMessage.newBuilder().setDataType(MyDataInfo.MyMessage.DataType.WorkerType)
//                    .setWorker(MyDataInfo.Worker.newBuilder()
//                            .setAge(5).setName("刘德华").build()).build();
//        }
        ctx.writeAndFlush(myMessage);

    }

    //读取事件时会触发
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("服务端回复的消息" + byteBuf.toString(CharsetUtil.UTF_8) + "-" + LocalDateTime.now().toString());
        System.out.println("服务器地址为:" + ctx.channel().remoteAddress());
        try {
            int a = 1 / 0;
        } catch (Exception e) {

        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("client exceptionCaught ");
        cause.printStackTrace();
        ctx.close();
        ctx.channel().close();
        ctx.pipeline().close();
        ctx.pipeline().channel().close();
    }
}