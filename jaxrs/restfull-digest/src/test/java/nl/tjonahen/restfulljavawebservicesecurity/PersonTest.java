package nl.tjonahen.restfulljavawebservicesecurity;

/*
 * Copyright (C) 2014 Philippe Tjon - A - Hen, philippe@tjonahen.nl
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.junit.Test;

/**
 *
 * @author Philippe Tjon - A - Hen, philippe@tjonahen.nl
 */
public class PersonTest {
    
    @Test
    public void test() throws NoSuchAlgorithmException, UnsupportedEncodingException {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String text = "javaee";
            md.update(text.getBytes("UTF-8")); // Change this to "UTF-16" if needed
            byte[] digest = md.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            String output = bigInt.toString(16);

            System.out.println(output);
    }
    
    @Test
    public void testMD5() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        
            MessageDigest md = MessageDigest.getInstance("MD5");
            String text = "1234";
            md.update(text.getBytes("UTF-8")); // Change this to "UTF-16" if needed
            byte[] digest = md.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            String output = bigInt.toString(16);

            System.out.println(output);

    }
    @Test
    public void testMD5Digest() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        
            MessageDigest md = MessageDigest.getInstance("MD5");
            String text = "admin@example.com:jdbcRealm:1234";
            md.update(text.getBytes("UTF-8")); // Change this to "UTF-16" if needed
            byte[] digest = md.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            String output = bigInt.toString(16);

            System.out.println("MD5Digest "+output);

    }
}
