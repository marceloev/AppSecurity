package com.appsecurity.Utils;

import java.text.Normalizer;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MapCrypt extends LinkedHashMap<Character, String> {

    public MapCrypt() {
        this.put(null, "0000");
        this.put('1', "asddasdas");
        this.put('A', "z544");
        this.put('a', "z445");
        this.put('B', "y998");
        this.put('b', "y899");
        this.put('C', "w211");
        this.put('c', "w112");
        this.put('D', "x879");
        this.put('d', "x978");
        this.put('E', "v987");
        this.put('e', "v789");
        this.put('F', "u895");
        this.put('f', "u598");
        this.put('G', "t122");
        this.put('g', "t221");
        this.put('H', "s214");
        this.put('h', "s412");
        this.put('I', "r421");
        this.put('i', "r124");
        this.put('J', "q548");
        this.put('j', "q845");
        this.put('K', "z215");
        this.put('k', "z512");
        this.put('L', "y123");
        this.put('l', "y321");
        this.put('M', "w844");
        this.put('m', "w448");
        this.put('N', "x124");
        this.put('n', "x421");
        this.put('O', "v013");
        this.put('o', "v310");
        this.put('P', "u021");
        this.put('p', "u210");
        this.put('Q', "t098");
        this.put('q', "t980");
        this.put('R', "s654");
        this.put('r', "s456");
        this.put('S', "r876");
        this.put('s', "r687");
        this.put('T', "q120");
        this.put('t', "q021");
        this.put('U', "z453");
        this.put('u', "z354");
        this.put('V', "y011");
        this.put('v', "y110");
        this.put('X', "w112");
        this.put('x', "w211");
        this.put('W', "x122");
        this.put('w', "x221");
        this.put('Y', "v001");
        this.put('y', "v100");
        this.put('Z', "u002");
        this.put('z', "200u");
    }

    public String getCrypt(Character key) {
        String temp = Normalizer.normalize(key.toString(), Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String strKey = pattern.matcher(temp).replaceAll("");
        return this.get(strKey.charAt(0));
    }

    public String getUncrypt(String value) throws ClassNotFoundException {
        if (!this.containsValue(value))
            throw new ClassNotFoundException(String.format("Não existe decriptografia para valor: %s", value));
        else {
            int index = this.values().stream().collect(Collectors.toList()).indexOf(value);
            int idx = 0;
            for (Character character : this.keySet()) {
                if (idx == index) return character.toString();
                else idx++;
            }
        }
        return null;
    }
}