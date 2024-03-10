package com.modern.exec;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CacheExample {

    private MessageDigest messageDigest;

    public static void main(String[] args) {
        new CacheExample().main();
    }

    public CacheExample() {
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * 계산패턴
     *  1. computeIfAbsent :제공된 키에 해당하는 값이 없으면(값이 없거나 null), 키를 이용해 새값을 계산하고 맵에 추가한다.
     *  2. computeIfPresent : 제공된 키가 존재하면 새 값을 계산하고 맵에 추가한다.
     *  3. compute : 제공된 키로 새 값을 계산하고 맵에 저장한다.
     *  정보를 캐시할때 computeIfAbsent를 활용 할수 이;ㅆ다. 파일 집합의 각 행을 파싱행  SHA-256(해시 알고리즘)을 계산한다고
     *  가정했을때 기존에 이미 데이터를 처리했다면 이 값을 다시 계산할 필요가 없다.
     * */
    private void main() {
        List<String> lines = Arrays.asList(
                " Nel   mezzo del cammin  di nostra  vita ",
                "mi  ritrovai in una  selva oscura",
                " che la  dritta via era   smarrita "
        );
        Map<String, byte[]> dataToHash = new HashMap<>();

        lines.forEach(line ->
                dataToHash.computeIfAbsent(line, this::calculateDigest));
        dataToHash.forEach((line, hash) ->
                System.out.printf("%s -> %s%n", line,
                        new String(hash).chars().map(i -> i & 0xff).mapToObj(String::valueOf).collect(Collectors.joining(", ", "[", "]"))));
    }

    private byte[] calculateDigest(String key) {
        return messageDigest.digest(key.getBytes(StandardCharsets.UTF_8));
    }

}
