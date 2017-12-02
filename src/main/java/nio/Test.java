package nio;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.Queue;

import org.omg.CORBA.SystemException;

public class Test {

	public static void main(String[] args) {
		ByteBuffer writeBuffer=ByteBuffer.allocate(128);
		writeBuffer.putChar('h');
		writeBuffer.putChar('e');
		writeBuffer.putChar('u');
		writeBuffer.putChar('u');
		writeBuffer.flip();
		System.out.println("Œª÷√"+writeBuffer.position()+"-"+writeBuffer.limit());
		writeBuffer.flip();
		System.out.println("Œª÷√"+writeBuffer.position()+"-"+writeBuffer.limit());
		
		while(writeBuffer.hasRemaining()) {
			System.out.println(writeBuffer.getChar());
		}
		
	}

}
