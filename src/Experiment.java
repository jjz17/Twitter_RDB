import redis.clients.jedis.Jedis;

public class Experiment {
  public static void main(String[] args) {
    Jedis jedis = new Jedis("127.0.0.1", 6379);
  }
}
