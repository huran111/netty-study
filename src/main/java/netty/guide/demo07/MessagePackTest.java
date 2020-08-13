package netty.guide.demo07;

import org.msgpack.MessagePack;
import org.msgpack.template.Templates;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-13 14:44
 */
public class MessagePackTest {
    public static void main(String[] args) throws IOException {
        List<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");
        list.add("C");
        MessagePack messagePack=new MessagePack();
        byte[] write = messagePack.write(list);
        List<String> read = messagePack.read(write, Templates.tList(Templates.TString));
        System.out.println(read);
    }
}