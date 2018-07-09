package com.appsecurity.Utils;

public class Password {

    private String password;
    private String uncryptedPassword;
    private MapCrypt mapCrypt = new MapCrypt();

    public Password(Boolean crypted, String valor) {
        if (crypted) {
            setPassword(valor);
            setUncryptedPassword(uncryptPassword(valor));
        } else {
            setPassword(cryptPassword(valor));
            setUncryptedPassword(valor);
        }
    }

    public Password(String password, String uncryptedPassword) {
        setPassword(password);
        setUncryptedPassword(uncryptedPassword);
    }

    private String cryptPassword(String uncryptedPassword) {
        StringBuilder buildCrypt = new StringBuilder();
        int number = uncryptedPassword.length();
        for (int i = 0; i < number; i++) {
            if (uncryptedPassword.substring(i, i + 1).matches("[0-9]") && i == 0)
                buildCrypt.append(uncryptedPassword.charAt(i));
            if (uncryptedPassword.substring(i, i + 1).matches("[0-9]") && i != 0)
                buildCrypt.append("-" + uncryptedPassword.charAt(i));
            else if (i == 0)
                buildCrypt.append(mapCrypt.getCrypt(uncryptedPassword.charAt(i)));
            else
                buildCrypt.append("-" + mapCrypt.getCrypt(uncryptedPassword.charAt(i)));
        }
        return buildCrypt.toString();
    }

    private String uncryptPassword(String cryptedPassword) {
        StringBuilder buildUncrypt = new StringBuilder();
        try {
            for (String map : cryptedPassword.split("-")) {
                if (map.isEmpty() || map.equals(null) || map.matches("[0-9]")) buildUncrypt.append(map);
                else buildUncrypt.append(mapCrypt.getUncrypt(map));
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return buildUncrypt.toString();
    }

    @Override
    public String toString() {
        return "Password{" +
                "password='" + password + '\'' +
                ", uncryptedPassword='" + uncryptedPassword + '\'' +
                '}';
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUncryptedPassword() {
        return uncryptedPassword;
    }

    public void setUncryptedPassword(String uncryptedPassword) {
        this.uncryptedPassword = uncryptedPassword;
    }
}