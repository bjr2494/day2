package edu.acc.j2ee.hubbub;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.codec.digest.Sha2Crypt;

public class UserDaoImpl implements UserDao {
    
    private static final Pattern USER_PATT = Pattern.compile("^\\w{6,12}$");
    private static final Pattern PASS_PATT = Pattern.compile("^[\\w\\.-]{8,16}$");
    private static final Pattern SALT_PATT = Pattern.compile("^(\\$\\d\\$.+)\\$");
    private final List<User> users = new ArrayList<>();
    
    @Override
    public void addUser(User u) {
        u.setPassword(Sha2Crypt.sha256Crypt(u.getPassword().getBytes()));
        users.add(u);
    }

    @Override
    public boolean validate(String username, String password) {
        if (username == null || password == null)
            return false;
        if (!USER_PATT.matcher(username).matches())
            return false;
        return PASS_PATT.matcher(password).matches();
    }

    @Override
    public User authenticate(String username, String password) {
        User user = this.findByUsername(username);
        if (user ==  null) return null;
        Matcher m = SALT_PATT.matcher(user.getPassword());
        boolean found = m.find();
        if (found) {
            String salt = m.group(1);
            String saltedCandidate = Sha2Crypt.sha256Crypt(password.getBytes(), salt);
            return saltedCandidate.equals(user.getPassword()) ? user : null;
        }
        else return null;
    }

    @Override
    public User findByUsername(String username) {
        for (User user : users)
            if (user.getUsername().equals(username))
                return user;
        return null;
    }
    
}
