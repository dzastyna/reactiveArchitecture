package bnsit.ms.arq.library;

public class User {
    private final String nickname;
    private final String password;
    private final String fullname;
    private final String address;
    private final String pesel;
    private long id;

    public User(String nickname, String password, String fullname, String address, String pesel) {

        this.nickname = nickname;
        this.password = password;
        this.fullname = fullname;
        this.address = address;
        this.pesel = pesel;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

    public String getFullname() {
        return fullname;
    }

    public String getAddress() {
        return address;
    }

    public String getPesel() {
        return pesel;
    }
}
