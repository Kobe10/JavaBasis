package nio;

import java.nio.channels.SelectionKey;
import java.util.Queue;

public interface IWorker {
	public void process(SelectionKey key, Queue<SelectionKey> operateQueue);

}
