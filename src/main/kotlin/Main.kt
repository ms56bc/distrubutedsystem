import org.apache.zookeeper.WatchedEvent
import org.apache.zookeeper.Watcher
import org.apache.zookeeper.ZooKeeper
import java.util.concurrent.Semaphore

object ApplicationKt: Watcher {
    private const val  ZOOKEEPER_ADDRESS = "localhost:2181"
    private const val TIMEOUT =3000
    private const val NAMESPACE  = "leaderelection"
    private final var zookeper: ZooKeeper = ZooKeeper(ZOOKEEPER_ADDRESS, TIMEOUT, this)

private val sem:  Semaphore = Semaphore(1)
    override fun process(event: WatchedEvent?) {
        when(event?.type){
            Watcher.Event.EventType.None -> if(event.state == Watcher.Event.KeeperState.SyncConnected){
                println("sucessfullly connected to zookeper")
            } else {
                println("disconnected from zookeeper")
                sem.release()
            }
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        run()
        sem.acquire()
        close()
    }
    fun close(){
        zookeper.close()
    }
    fun run(){
        sem.acquire()
    }

}