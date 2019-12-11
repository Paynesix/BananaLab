package homework.xy.session5;

import javax.xml.bind.SchemaOutputResolver;

/**
 * @ClassName BananaMapTest
 * @Description TODO
 * @Author xy
 * @Date 2019/8/16 16:02
 * @Version 1.0
 **/
public class BananaMapTest {

    public static void main(String[] args) {
        BananaMap map = new BananaMap();
        map.put(1, 1);
        map.put(2, 2);
        System.out.println(map.get(2) + ", " + map.get(1));

        map.remove(2);
        System.out.println(map.get(1));
        System.out.println(map.get(2));
        System.out.println(map.size());
        System.out.println(map.isEmpty());
        map.remove(1);
        System.out.println(map.isEmpty());
        map.remove(3);


        BananaMap map1 = new BananaMap(1<<32);
        System.out.println(map1.size());
    }
}
