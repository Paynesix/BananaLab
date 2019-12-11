package homework.xy.session5;

import java.util.BitSet;

/**
 * 去重统计的布隆过滤器
 *
 * @param <K>
 */
public class BananaBloomFilter<K> implements CountingInterface<K> {

    final static int SIZE = 1 << 24;
    BitSet bitSet = new BitSet(SIZE);
    private static final int seeds[] = new int[]{3, 5, 7, 9, 11, 13, 17, 19};
    BananaHash[] hashs = new BananaHash[seeds.length];

    public static void main(String[] args) {
        String email = "zhenlingcn@126.com";
        BananaBloomFilter bloomDemo = new BananaBloomFilter();
        System.out.println(email + "是否在列表中： " + bloomDemo.contains(email));
        bloomDemo.add(email);
        System.out.println(email + "是否在列表中： " + bloomDemo.contains(email));
        email = "zhenlingcn@163.com";
        System.out.println(email + "是否在列表中： " + bloomDemo.contains(email));
    }

    /**
     * @param str
     * @return
     */
    private boolean contains(String str) {
        boolean have = true;
        for (BananaHash hash : hashs) {
            have &= bitSet.get(hash.getHash(str));
        }
        return have;
    }

    @Override
    public void add(K key) {

    }

    @Override
    public int size() {
        return 0;
    }
}
