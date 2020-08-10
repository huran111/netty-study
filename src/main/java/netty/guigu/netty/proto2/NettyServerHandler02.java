package netty.guigu.netty.proto2;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.guigu.netty.proto.StudentProto;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-08 21:53
 */
public class NettyServerHandler02 extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {
        final MyDataInfo.MyMessage.DataType dataType = msg.getDataType();
        if (dataType == MyDataInfo.MyMessage.DataType.StudentType) {
            final MyDataInfo.Student student = msg.getStudent();
            System.out.println(student.getId() + ":" + student.getName());
        } else if (dataType == MyDataInfo.MyMessage.DataType.WorkerType) {
            final MyDataInfo.Worker worker = msg.getWorker();
            System.out.println(worker.getAge() + ":" + worker.getName());
        } else {
            System.out.println("传输类型不对");
        }
    }

    /**
     * 读取数据完毕
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端 喵喵1.".getBytes()));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("exceptionCaught");
        ctx.channel().close();
    }
}