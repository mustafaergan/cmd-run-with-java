package com.bat.bat;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

@RestController
@RequestMapping(path ="/admin")
public class AdminController {

    /** Windows kullanıcı adı: yalnızca harf, rakam, nokta, alt çizgi ve tire; en fazla 20 karakter. */
    private static final Pattern KULLANICI_ADI = Pattern.compile("^[A-Za-z0-9._-]{1,20}$");

    @GetMapping(value = "/add/{name}")
    @ResponseStatus(HttpStatus.OK)
    public String addAdmin(@PathVariable("name") String name)  throws IOException {
        return calistir(dogrula(name), "/add");
    }

    @GetMapping(value = "/remove/{name}")
    @ResponseStatus(HttpStatus.OK)
    public String removeAdmin(@PathVariable("name") String name)  throws IOException{
        return calistir(dogrula(name), "/delete");
    }

    private static String dogrula(String name) {
        if (name == null || !KULLANICI_ADI.matcher(name).matches()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Geçersiz kullanıcı adı");
        }
        return name;
    }

    /** Komut kabuk (cmd /c) üzerinden değil, argüman dizisiyle doğrudan net.exe'ye verilir. */
    private static String calistir(String name, String islem) throws IOException {
        ProcessBuilder builder = new ProcessBuilder(
                "net", "localgroup", "administrators", name, islem);
        builder.redirectErrorStream(true);
        Process p = builder.start();
        StringBuilder sb = new StringBuilder();
        try (BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            String line;
            while ((line = r.readLine()) != null) {
                System.out.println(line);
                sb.append(line);
            }
        }
        return sb.toString();
    }
}
