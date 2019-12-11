package homework.xy.session5;

/**
 * @ClassName BananaHash
 * @Description TODO
 * @Author xy
 * @Date 2019/8/20 9:53
 * @Version 1.0
 **/
public class BananaHash {
        private int seed = 0;

        public BananaHash(int seed) {
            this.seed = seed;
        }

        public int getHash(String string) {
            int val = 0;
            int len = string.length();
            for (int i = 0; i < len; i++) {
                val = val * seed + string.charAt(i);
            }
            return val & ((1 << 24) - 1);
        }
}
